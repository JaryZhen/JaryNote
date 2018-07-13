package jary.note.undertow.handle.trace;

import brave.http.HttpServerAdapter;
import brave.http.HttpServerHandler;
import brave.propagation.TraceContext;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ExceptionHandler;
import io.undertow.util.HeaderMap;

public class HttpTrace {

    HttpServerHandler<HttpServerExchange, HttpServerExchange> serverHandler;
    TraceContext.Extractor<HeaderMap> extractor;

    UndertowHttpTrace undertowHttpTrace;

    public HttpTrace(HttpServerAdapter<HttpServerExchange, HttpServerExchange> adapter) {
        undertowHttpTrace = UndertowHttpTrace.getInstance();
        extractor = undertowHttpTrace.extractor;
        serverHandler = HttpServerHandler.create(undertowHttpTrace.httpTracing, adapter);
    }

    public brave.Span startNew(HttpServerExchange exchange) {
        brave.Span span = serverHandler.handleReceive(extractor, exchange.getRequestHeaders(), exchange);
        return span.start();
    }

    public void end(HttpServerExchange exch, brave.Span span) {
        serverHandler.handleSend(exch, exch.getAttachment(ExceptionHandler.THROWABLE), span);
    }

}
