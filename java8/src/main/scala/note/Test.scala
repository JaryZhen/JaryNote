package note

/**
  * Created by Jary on 2018/1/17 0017.
  */
object Test {
  def main(args: Array[String]): Unit = {
    println(hf(-99, x=>Math.abs(x).toString))
    println(hf(55, swc(_)))

    def swc( a: Int):String={
      val b = a+1
      b.toString
    }
  }

  def hf(i:Int, f: Int=>String) = f(i+1)

}
