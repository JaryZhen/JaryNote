package note.jary.undertow.handler

import io.undertow.server.{HttpHandler, HttpServerExchange}
import io.undertow.util.Headers


class InitHandler extends HttpHandlerSupport {

  override protected def handle(exchange: HttpServerExchange): Unit = {
    exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
    exchange.getResponseSender.send("Hello World")
  }
}
