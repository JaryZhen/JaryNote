package jary.note.undertow.handle.trace;

import brave.Tracer;
import brave.Tracing;
import brave.http.HttpServerAdapter;
import brave.http.HttpServerHandler;
import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import zipkin2.Endpoint;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.io.IOException;
import java.net.InetSocketAddress;

class UndertowTraceService {

    public static CurrentTraceContext currentTraceContext;
    public static HttpServerHandler<HttpServerExchange, HttpServerExchange> braveServerHandler;
    public static TraceContext.Extractor<HeaderMap> extractor;

    public static Sender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
    public static AsyncReporter<zipkin2.Span> spanReporter = AsyncReporter.create(sender);

    public  static Tracing tracing = Tracing.newBuilder()
            .localServiceName("Undertow-Service")
            .spanReporter(spanReporter)
            .sampler(brave.sampler.Sampler.create(1))
            .build();

    HttpTracing httpTracing = HttpTracing.create(tracing);

    UndertowTraceService(){
        extractor = httpTracing.tracing().propagation().extractor(GETTER);
        braveServerHandler = HttpServerHandler.create(httpTracing, new Adapter());
        currentTraceContext = httpTracing.tracing().currentTraceContext();
    }

    Tracer tracer = tracing.tracer();
    Tracer tracerHttp = httpTracing.tracing().tracer();

    public void close() throws IOException {
        tracing.close();
        spanReporter.close();
        sender.close();
    }

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
