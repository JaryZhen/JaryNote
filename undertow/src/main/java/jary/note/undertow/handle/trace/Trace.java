package jary.note.undertow.handle.trace;

import brave.Tracer;
import note.jary.undertow.trace.TraceData;

public class Trace {
    Tracer zipkinTracer;
    TraceData data;
    public Trace(){
        zipkinTracer = UndertowTrace.getInstance().zipkinTracer;
    }
    public brave.Span startNew(String spanName) {
        brave.Span span = zipkinTracer.newTrace().name(spanName).kind((brave.Span.Kind.SERVER));
        return span.start();
    }

    public brave.Span startNewChild(String spanName, brave.Span parentSpan) {
        brave.Span span = zipkinTracer.newChild(parentSpan.context()).name(spanName).kind((brave.Span.Kind.SERVER));
        return span.start();
    }

    public void end(brave.Span span) {
        span.finish();
    }

}
