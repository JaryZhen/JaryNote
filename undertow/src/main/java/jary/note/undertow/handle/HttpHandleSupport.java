package jary.note.undertow.handle;

import brave.Span;
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
import jary.note.undertow.handle.trace.HttpTrace;
import jary.note.undertow.handle.trace.UndertowHttpTrace;
import jary.note.undertow.handle.trace.UndertowTrace;
import zipkin2.Endpoint;

import java.net.InetSocketAddress;

public class HttpHandleSupport implements HttpHandler {

    HttpHandler next;
    HttpTrace tracer;

    public HttpHandleSupport() {
        next = new InitHandler();
        tracer = new HttpTrace(new Adapter());
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (!exchange.isComplete()) {
            brave.Span span = tracer.startNew(exchange);
            //trace("haha", ZipkinTracer, span);

            exchange.addExchangeCompleteListener((exch, nextListener) -> {
                try {
                    //System.out.println();
                    nextListener.proceed();
                } finally {
                    tracer.end(exch, span);
                }
            });
            next.handleRequest(exchange);
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
