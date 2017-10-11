package demo.app

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive


/**
  * Created by Jary on 2016/8/18.
  */
class HelloAtor extends Actor{

  def receive = {
    case "hello" => println("您好！")
    case _       => println("您是?")
  }
}

object Main {
  def main(args: Array[String]) {
    println("asdfasf")
    val system = ActorSystem("HelloSystem")
    // 缺省的Actor构造函数
    val helloActor = system.actorOf(Props[HelloAtor], name = "helloactor")
    helloActor ! "hello"
    helloActor ! "喂"
  }
}
