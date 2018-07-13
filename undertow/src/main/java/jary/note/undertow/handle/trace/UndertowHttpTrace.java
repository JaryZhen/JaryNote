package jary.note.undertow.handle.trace;

import brave.SpanCustomizer;
import brave.http.HttpAdapter;
import brave.http.HttpClientParser;
import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import io.undertow.util.HeaderMap;
import zipkin.TraceKeys;

public class UndertowHttpTrace {

    public static CurrentTraceContext currentTraceContext;
    public static TraceContext.Extractor<HeaderMap> extractor;
    public static HttpTracing httpTracing;

    private static class help {
        private static UndertowHttpTrace instance = new UndertowHttpTrace();
    }

    public static UndertowHttpTrace getInstance() {
        return help.instance;
    }

    private UndertowHttpTrace() {
        httpTracing = HttpTracing.create(UndertowTrace.getInstance().tracing).toBuilder()
                .clientParser(new HttpClientParser() {
                    @Override
                    public <Req> void request(HttpAdapter<Req, ?> adapter, Req req, SpanCustomizer customizer) {
                        customizer.name(spanName(adapter, req));
                        customizer.tag(TraceKeys.HTTP_URL, adapter.url(req));
                    }
                    @Override
                    public <Req> String spanName(HttpAdapter<Req, ?> adapter, Req req) {
                        return adapter.method(req) + " - " + adapter.path(req);
                    }
                })
                .build();

        extractor = httpTracing.tracing().propagation().extractor(GETTER);
        currentTraceContext = httpTracing.tracing().currentTraceContext();
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
