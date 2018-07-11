package note.jary.undertow.handler

import io.undertow.server.{HttpHandler, HttpServerExchange}
import io.undertow.util.{HeaderMap, Headers, HttpString}
import org.apache.logging.log4j.LogManager
import scala.util.control.NonFatal


abstract class HttpHandlerSupport extends HttpHandler {


  private[this] val accessLogger = LogManager.getLogger("access")

  override def handleRequest(exchange: HttpServerExchange): Unit = {

   //exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
    //exchange.getResponseSender.send("Hello World")

    try {
      handle(exchange)
      exchange.endExchange()
      if (accessLogger.isTraceEnabled) {

      }
    } catch {
      case NonFatal(t) ⇒
        exchange.endExchange()
    }

  }

  protected def handle( exchange: HttpServerExchange): Unit

}
