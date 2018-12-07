import javax.lang.model.element.NestingKind

import scala.concurrent.Future

/**
  * Created by Jary on 2018/3/12 0012.
  */
object Demo {

  def main(args: Array[String]): Unit = {

    val a = None
    val b = Some(2)
    val data = Array(1, 2, 3, 4, 5)

    def f (a: Int, b: Int) ={
      a-b
    }
    1.to(10)

    val c = data.foldLeft(0)(f(_,_))
    val c2 = data.foldRight(0)(_ + _)
    println(c2)

    val cc = for(i <- 1 to 3;
        a = i + "k";
        j <- 4 to 6) yield (i+"-"+a+"-"+j)

    cc.foreach(println)
  }
}
