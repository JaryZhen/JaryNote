package chp04

object Test {

  def main(args: Array[String]): Unit = {

    val a = None
    val b = Some(2)

    a.map(println(_))
    b.map(println(_))
    println(map2(a,b)(parseInt))
  }

  def parseInt(a: Int, b: Int): Int ={
    a+b
  }

  def map2[A,B,C](a: Option[A],b: Option[B])(f: (A, B) => C): Option[C] = {

    val re = a match {
      case Some(k) => b match {
        case Some(kk) => Some(f(k, kk))
        case None => None
      }
      case None => None
    }

    val re2 = a.flatMap { a1 =>
      b.map { b1 =>
        f(a1, b1)
      }
    }

    val re3 = for {
      aa <- a
      bb <- b
    } yield f(aa,bb)

    re3
  }
}
