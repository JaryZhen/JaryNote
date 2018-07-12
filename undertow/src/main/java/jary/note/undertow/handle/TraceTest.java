package jary.note.undertow.handle;

import brave.Tracer;
import jary.note.undertow.handle.trace.UndertowTrace;
public class TraceTest {
    Tracer tracer;
    public TraceTest() {
        tracer = UndertowTrace.getInstance().tracer;
    }

    public void test() throws InterruptedException {
        brave.Span span = tracer.newTrace().name("test").kind(brave.Span.Kind.SERVER);
        span.start();
        nextTest(tracer, span);
        Thread.currentThread().sleep(1000);

        span.finish();
    }

    public void nextTest(Tracer tracer, brave.Span pspan) throws InterruptedException {
        brave.Span span = tracer.newChild(pspan.context()).name("nextTest").kind(brave.Span.Kind.SERVER);
        span.start();

        childTest(tracer,span);
        Thread.currentThread().sleep(1000);

        span.finish();
    }

    public void childTest(Tracer tracer, brave.Span pspan) throws InterruptedException {

        brave.Span span = tracer.newChild(pspan.context()).name("childTest").kind(brave.Span.Kind.SERVER);

        span.start();
        Thread.currentThread().sleep(1000);

        span.finish();
    }
}
