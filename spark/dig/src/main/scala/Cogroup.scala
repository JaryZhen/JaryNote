import org.apache.spark.sql.SparkSession

object Cogroup {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("SparkTC").master("local")
      .getOrCreate()

    val sc = spark.sparkContext
    var rdd1 = sc.makeRDD(Array(("A","1"),("B","2"),("C","3")),2)
    var rdd2 = sc.makeRDD(Array(("A","a"),("C","c"),("D","d")),2)

    var rdd3 = rdd1.cogroup(rdd2)
    rdd3.foreach(println)
  }
}
