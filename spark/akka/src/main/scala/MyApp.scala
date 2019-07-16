/**
  * Created by Jary on 2017/10/31 0031.
  */
import akka.actor.{Actor, Props, ActorSystem}
import com.typesafe.config.ConfigFactory


object MyApp extends App {
  val actorSystem1 = ActorSystem("actorSystem1", ConfigFactory.parseString("""
    akka {
       actor {
           provider = "akka.remote.RemoteActorRefProvider"
             }
       remote {
           transport = ["akka.remote.netty.tcp"]
       netty.tcp {
           hostname = "localhost"
           port = 2209
                 }
             }
        }
                                                                           """))


  val actorSystem2 = ActorSystem("actorSystem2")


  actorSystem1.actorOf(Props(new Actor {
    def receive = {
      case x: String =>
        Thread.sleep(1000)
        println("RECEIVED MESSAGE: " + x)
    } }), "simplisticActor")



  val remoteActor = actorSystem2.actorSelection("akka://actorSystem1@localhost:2209/user/simplisticActor")

  remoteActor ! "TEST 1"
  remoteActor ! "TEST 2"



  Thread.sleep(1000)

  //actorSystem1.stop(se)
  //actorSystem2.shutdown()
}
