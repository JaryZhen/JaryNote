package jary.note.undertow.handle;

import brave.Tracer;
import jary.note.undertow.handle.trace.UndertowTrace;
public class TraceTest {
    Tracer tracer;
    public TraceTest() {
        tracer = UndertowTrace.getInstance().tracer;
    }

    public void test() {
        brave.Span span = tracer.newTrace().name("test").kind(brave.Span.Kind.SERVER);
        span.start();

        childTest(tracer, span);
        span.finish();
    }

    public void childTest(Tracer tracer, brave.Span pspan) {

        brave.Span span = tracer.newChild(pspan.context()).name("childTest").kind(brave.Span.Kind.SERVER);
        span.start();

        span.finish();
    }
}
