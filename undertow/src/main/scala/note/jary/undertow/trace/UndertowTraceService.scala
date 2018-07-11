package note.jary.undertow.trace

import javax.inject.Inject

import akka.actor.ActorSystem
import brave.Tracing
import brave.sampler.Sampler
import zipkin2.reporter.AsyncReporter
import zipkin2.reporter.okhttp3.OkHttpSender

import scala.concurrent.ExecutionContext

/**
  * @param actorSystem
  */
class UndertowTraceService @Inject()(actorSystem: ActorSystem) extends UndertowTraceServiceLike {

  implicit val executionContext: ExecutionContext = actorSystem.dispatchers.lookup("undertow-akka")

  val tracing = Tracing.newBuilder()
    .localServiceName("Undertow-Service")
    .spanReporter(AsyncReporter.create(OkHttpSender.create(("http://localhost:9411/api/v2/spans"
      ))))
    .sampler(Sampler.create(1))
    .build()
}
