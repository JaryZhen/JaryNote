package jary.note.undertow.handle;

import jary.note.undertow.handle.trace.Trace;
import jary.note.undertow.handle.trace.TraceData;

public class TraceTest {
    Trace tracer;
    public TraceTest() {
        tracer = new Trace();
    }


    public void test() {
        brave.Span span = tracer.startNew("test");

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
