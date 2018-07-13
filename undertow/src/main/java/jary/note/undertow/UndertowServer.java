package jary.note.undertow;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import jary.note.undertow.handle.HttpHandleSupport;

public class UndertowServer {
    public static void main(final String[] args) {

        PathHandler path = new PathHandler();
        path.addExactPath("/hello", new HttpHandleSupport());
        //path.addExactPath("/",new InitHandler());

        Undertow server = Undertow.builder()
                .addHttpListener(8088, "localhost")
                .setHandler(path).build();
        server.start();

    }
}
