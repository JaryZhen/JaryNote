package jary.note.core;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import jary.note.nonblocking.HelloWorldHandler;

public class HandlerChainWrap implements HandlerWrapper {

    @Override
    public HttpHandler wrap(HttpHandler handler) {

        return new HelloWorldHandler();
    }
}
