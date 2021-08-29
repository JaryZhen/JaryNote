import java.beans.Transient
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.api.java.function.Function
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


    @Transient
    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)

    val counts = distData.map(word => (word, 1))
      .reduceByKey(_ + _).collect()




  }
}
