
/**
  * Created by Jary on 2018/3/12 0012.
  */
object Demo {
  def main(args: Array[String]): Unit = {
   for {i <- 0 to (5)
    b <- 3 to (8)
        if (i==b)} yield{

      println(i +" "+b)
   }
  }
}
