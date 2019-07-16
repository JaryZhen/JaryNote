package demo.app

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive


/**
  * Created by Jary on 2016/8/18.
  */
class HelloAtor extends Actor{


  override def preStart(): Unit = {
    super.preStart()
    println("pre on start")
  }
  def receive = {
    case "hello" => println("您好！")
    case "stop" => println("will stop ...")
    case _       => println("您是?")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    super.preRestart(reason, message)

    println("pre on stop")
  }
}

object Main {
  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    println(system.toString)
    // 缺省的Actor构造函数
    val helloActor = system.actorOf(Props[HelloAtor], name = "helloactor")
    helloActor ! "hello"
    helloActor ! "喂"
    Thread.sleep(1000)
    helloActor.tell("hello",helloActor)
  }
}
