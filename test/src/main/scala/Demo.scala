import javax.lang.model.element.NestingKind

/**
  * Created by Jary on 2018/3/12 0012.
  */
object Demo {

  def main(args: Array[String]): Unit = {

    def partiall[A,B,C](a:A ,f:(A,B) => C): B => C = (b: B) => f(a,b)

    def curry [A,B,C](f: (A,B)=> C) : A => (B => C) =
      a=>b => f(a,b)

    def BC[B,C](f: B =>C): String= {

    "sds"
    }

    val lessThan = new Function2[Int , Int , Boolean] {
      override def apply(v1: Int, v2: Int): Boolean = v1 == v2
    }

    def isB (f:(Int ,Int => Boolean)){

    }
  }
}
