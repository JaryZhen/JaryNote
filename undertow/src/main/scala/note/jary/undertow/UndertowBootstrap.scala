package note.jary.undertow

import io.undertow.server.handlers.PathHandler
import io.undertow.{Undertow, UndertowOptions}
import note.jary.undertow.handler.InitHandler

object UndertowBootstrap extends App {

  private[this] val port: Int = 8088
  private[this] val host: String = "localhost"


  val path = new PathHandler()
  path.addExactPath("/hello", new InitHandler)

  val builder = Undertow.builder()
    .addHttpListener(port, host)
    .setHandler(path)

  val undertow: Undertow = builder.build()

  undertow.start()

}
