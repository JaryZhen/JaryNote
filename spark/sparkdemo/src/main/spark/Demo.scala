<<<<<<< HEAD
import java.beans.Transient
import java.util.Properties
=======
package main.spark

import org.apache.spark.sql.{SQLContext, SparkSession}
>>>>>>> f6009d72fe1610872cd837c7e549b484255188aa

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

<<<<<<< HEAD

    @Transient
=======
>>>>>>> f6009d72fe1610872cd837c7e549b484255188aa
    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)

    val counts = distData.map(word => (word, 1))
<<<<<<< HEAD
      .reduceByKey(_ + _).collect()




=======
      counts.reduceByKey(_ + _).foreach(print(_))
>>>>>>> f6009d72fe1610872cd837c7e549b484255188aa
  }
}
