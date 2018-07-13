package jary.note.undertow.handle.trace;

import brave.Tracer;
import brave.Tracing;
import brave.propagation.StrictCurrentTraceContext;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;


public class UndertowTrace {

    private String ZIPKIN_URL = "http://127.0.0.1:9411/api/v2/spans";
    private String TRACE_SERVER = "undertow-service";

    private OkHttpSender sender;
    private AsyncReporter<Span> spanReporter;
    public Tracing tracing;
    public Tracer zipkinTracer;

    private static class help {
        private static UndertowTrace singleton = new UndertowTrace();
    }

    public static UndertowTrace getInstance() {
        return help.singleton;
    }

    private UndertowTrace() {
        sender = OkHttpSender.create(ZIPKIN_URL);
        spanReporter = AsyncReporter.create(sender);
        tracing = Tracing.newBuilder()
                .localServiceName(TRACE_SERVER)
                //.currentTraceContext(new StrictCurrentTraceContext())
                .spanReporter(spanReporter)
                .build();
        zipkinTracer = tracing.tracer();
    }

    public void close() {
        tracing.close();
        spanReporter.close();
        sender.close();
    }
}
