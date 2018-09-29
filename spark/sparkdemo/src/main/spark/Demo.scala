import org.apache.spark.sql.{SQLContext, SparkSession}

/**
  * Created by Jary on 2018/3/12 0012.
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
   .builder
   .appName("SparkTC").master("local")
   .getOrCreate()

    val sc = spark.sparkContext
    val ssc = new SQLContext(sc)

    ssc.sparkSession.

    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)

    val counts = distData.map(word => (word, 1))
      counts.
      .reduceByKey(_ + _).foreach(print(_))
  }
}
