package jary.note.undertow.handle;

import brave.Tracer;
import brave.Tracing;
import brave.http.HttpServerAdapter;
import brave.http.HttpServerHandler;
import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ExceptionHandler;
import io.undertow.util.HeaderMap;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.net.InetSocketAddress;

public class HttpHandleSupport implements HttpHandler {


    static CurrentTraceContext currentTraceContext;
    static HttpServerHandler<HttpServerExchange, HttpServerExchange> serverHandler;
    static TraceContext.Extractor<HeaderMap> extractor;
    static HttpHandler next;

    protected void handle(HttpServerExchange exchange) {
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (!exchange.isComplete()) {

            OkHttpSender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
            AsyncReporter<Span> spanReporter = AsyncReporter.create(sender);

            // Create a tracing component with the service name you want to see in Zipkin.
            Tracing tracing = Tracing.newBuilder()
                    .localServiceName("undertow-service")
                    .spanReporter(spanReporter)
                    .build();

            Propagation.Getter<HeaderMap, String> GETTER = new Propagation.Getter<HeaderMap, String>() {
                @Override
                public String get(HeaderMap carrier, String key) {
                    return carrier.getFirst(key);
                }

                @Override
                public String toString() {
                    return "HttpServerRequest::getHeader";
                }
            };

            HttpTracing httpTracing = HttpTracing.create(tracing);

            extractor = httpTracing.tracing().propagation().extractor(GETTER);
            serverHandler = HttpServerHandler.create(httpTracing, new Adapter());
            currentTraceContext = httpTracing.tracing().currentTraceContext();
            //extractor = httpTracing.tracing().\propagation().extractor(Request::getHeader);


            brave.Span span = serverHandler.handleReceive(extractor, exchange.getRequestHeaders(), exchange);
            exchange.addExchangeCompleteListener((exch, nextListener) -> {
                try {
                    handle(exchange);
                    nextListener.proceed();
                } finally {
                    serverHandler.handleSend(exch, exch.getAttachment(ExceptionHandler.THROWABLE), span);

                    tracing.close();
                    spanReporter.close();
                    sender.close();
                }
            });

            try (CurrentTraceContext.Scope scope = currentTraceContext.newScope(span.context())) {
                next.handleRequest(exchange);
            } catch (Exception | Error e) { // move the error to where the complete listener can see it
                exchange.putAttachment(ExceptionHandler.THROWABLE, e);
                throw e;
            }
        } else {
            next.handleRequest(exchange);
        }
    }

    static final class Adapter extends HttpServerAdapter<HttpServerExchange, HttpServerExchange> {
        @Override
        public String method(HttpServerExchange request) {
            return request.getRequestMethod().toString();
        }

        @Override
        public String path(HttpServerExchange request) {
            return request.getRequestPath();
        }

        @Override
        public String url(HttpServerExchange request) {
            return request.getRequestURL();
        }

        @Override
        public String requestHeader(HttpServerExchange request, String name) {
            return request.getRequestHeaders().getFirst(name);
        }

        @Override
        public Integer statusCode(HttpServerExchange response) {
            return response.getStatusCode();
        }

        @Override
        public boolean parseClientAddress(HttpServerExchange req, Endpoint.Builder builder) {
            if (super.parseClientAddress(req, builder)) return true;
            InetSocketAddress addr = (InetSocketAddress) req.getConnection().getPeerAddress();
            if (builder.parseIp(addr.getAddress())) {
                builder.port(addr.getPort());
                return true;
            }
            return false;
        }
    }

}
