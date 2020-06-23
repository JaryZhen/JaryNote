import scala.concurrent.Future
import scala.util.Random

object Test {

  import scala.concurrent.ExecutionContext.Implicits.global

  val random = Random
  val listOfFuture = List.fill(10)(Future {
    random.nextInt(100)
  })
  val futureList1 = Future.sequence(listOfFuture)
  val sum1 = futureList1.map(_.sum)

  import scala.concurrent.ExecutionContext.Implicits.global

  val futureList = Future.traverse((1 to 100).toList)(n => Future(n * n))
  val sum2 = futureList.map(_.sum)
  println(sum2)

  val futures = (1 to 100).map(n => Future(n * n))
  val sum = Future.reduce(futures)(_ + _)

  println(sum)

  def par[A, B, C](a: A, f: (A, B) => C): (B => C) =
    (b: B) => f(a, b)

  def f1[A, B, C](a: A, b: B): C = ???

  def main(args: Array[String]): Unit = {

    par(1, f1(2, 2))
  }
}
