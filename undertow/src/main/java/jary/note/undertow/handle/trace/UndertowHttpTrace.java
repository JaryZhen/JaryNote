package jary.note.undertow.handle.trace;

import brave.SpanCustomizer;
import brave.http.*;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import io.undertow.util.HeaderMap;
import zipkin.TraceKeys;

public class UndertowHttpTrace {

    public CurrentTraceContext currentTraceContext;
    public TraceContext.Extractor<HeaderMap> extractor;
    public HttpTracing httpTracing;

    public  UndertowHttpTrace() {
        httpTracing = HttpTracing.newBuilder(UndertowTrace.getInstance().tracing)
                .serverParser(new HttpTraceClientParser())
                .build();

        extractor = httpTracing.tracing().propagation().extractor(GETTER);
        currentTraceContext = httpTracing.tracing().currentTraceContext();
    }

    class HttpTraceClientParser extends HttpServerParser{
        @Override
        public <Req> void request(HttpAdapter<Req, ?> adapter, Req req, SpanCustomizer customizer) {
            customizer.name(spanName(adapter, req));
            customizer.tag(TraceKeys.HTTP_URL, adapter.url(req));
        }
        @Override
        public <Req> String spanName(HttpAdapter<Req, ?> adapter, Req req) {
            return adapter.method(req) + " - " + adapter.path(req);
        }
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
}
