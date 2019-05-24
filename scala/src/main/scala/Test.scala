import scala.concurrent.{Await, Future}
import scala.util.Random

object Test extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  val random = Random
  val listOfFuture = List.fill(10)(Future{random.nextInt(100)})
  val futureList1 = Future.sequence(listOfFuture)
  val sum1 = futureList1.map(_.sum)

  import scala.concurrent.ExecutionContext.Implicits.global
  val futureList = Future.traverse((1 to 100).toList)(n => Future(n * n))
  val sum2 = futureList.map(_.sum)
  println(sum2)

  val futures = (1 to 100).map(n => Future(n * n))
  val sum = Future.reduceLeft(futures)(_ + _)

  println(sum)

}
