package jary.note.undertow.handle;

import brave.Tracer;
import brave.Tracing;
import io.undertow.server.HttpHandler;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class TraceTest {
    HttpHandler next;
    OkHttpSender sender;
    AsyncReporter<Span> spanReporter;
    Tracing tracing;

    public TraceTest() {
        sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
        spanReporter = AsyncReporter.create(sender);
        next = new InitHandler();
        tracing = Tracing.newBuilder()
                .localServiceName("undertow-service")
                .spanReporter(spanReporter)
                .build();
    }


    public void test() throws InterruptedException {
        Tracer tracer = tracing.tracer();
        brave.Span span = tracer.newTrace().name("test").kind(brave.Span.Kind.SERVER);

        span.start();
        Thread.currentThread().sleep(1000);

        span.finish();
    }
}
