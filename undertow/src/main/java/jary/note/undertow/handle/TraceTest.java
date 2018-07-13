package jary.note.undertow.handle;

import brave.propagation.TraceContext;
import jary.note.undertow.handle.trace.Trace;
import jary.note.undertow.handle.trace.TraceData;

public class TraceTest {
    Trace tracer;
    brave.Span parentSpan;
    public TraceTest(brave.Span span) {
        tracer = new Trace();
        parentSpan = span;
        test();
    }


    public void test() {
        brave.Span span = tracer.startNewChild("test",parentSpan);

        childTest(span);
        //...
        tracer.end(span);
    }

    public void childTest(brave.Span pspan) {

        brave.Span childSpan = tracer.startNewChild("childSpan",pspan);
        //...
        tracer.end(childSpan);
    }
}
