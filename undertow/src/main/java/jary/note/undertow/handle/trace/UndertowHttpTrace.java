package jary.note.undertow.handle.trace;

import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import io.undertow.util.HeaderMap;

public class UndertowHttpTrace {

    public CurrentTraceContext currentTraceContext;
    public TraceContext.Extractor<HeaderMap> extractor;
    public HttpTracing httpTracing;

    private static class help {
        private static UndertowHttpTrace instance = new UndertowHttpTrace();
    }

    public static UndertowHttpTrace getInstance(){
        return help.instance;
    }
    private UndertowHttpTrace(){
        httpTracing = HttpTracing.create(UndertowTrace.getInstance().tracing);
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
