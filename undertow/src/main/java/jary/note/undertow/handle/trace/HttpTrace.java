package jary.note.undertow.handle.trace;

import brave.http.HttpServerAdapter;
import brave.http.HttpServerHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ExceptionHandler;

public class HttpTrace {

    HttpServerHandler<HttpServerExchange, HttpServerExchange> serverHandler;

    UndertowHttpTrace undertowHttpTrace;

    public HttpTrace(HttpServerAdapter<HttpServerExchange, HttpServerExchange> adapter) {
        undertowHttpTrace = new UndertowHttpTrace();
        serverHandler = HttpServerHandler.create(undertowHttpTrace.httpTracing, adapter);
    }

    public brave.Span startNew(HttpServerExchange exchange) {
        brave.Span span = serverHandler.handleReceive(undertowHttpTrace.extractor, exchange.getRequestHeaders(), exchange);
        return span.start();
    }

    public void end(HttpServerExchange exch, brave.Span span) {
        serverHandler.handleSend(exch, exch.getAttachment(ExceptionHandler.THROWABLE), span);
    }

}
