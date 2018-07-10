package jary.note.undertow.handle;


import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;


public class HelloHandler extends AbstractHandle {
    @Override
    protected void  handle(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Hello World");
    }
}
