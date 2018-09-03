package chp03

object Test01 {
  def fibonacci(n: Int): Int =
    if (n <= 2)
      1
    else
      fibonacci(n-1) + fibonacci(n-2)

  def main(args: Array[String]): Unit = {
    fibonacci(6)

    List(1,2,3,4) match{
      case Nil => 1
      case _ :: _ :: h :: _ => println(h)
    }
  }

  def partiall[A,B,C](a:A ,f:(A,B) => C): B => C =
    (b: B) => f(a,b)

  def curry [A,B,C](f: (A,B)=> C) : A => (B => C) =
    a => b => f(a,b)

  def uncurry[A,B,C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)
}
