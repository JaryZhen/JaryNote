import java.io.{ObjectInputStream, ObjectOutputStream}

import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession

import scala.reflect.ClassTag
import org.apache.spark.streaming.StreamingContext

case class BroadcastWrapper [T: ClassTag](@transient private val sc: SparkContext,
                                          @transient private val _v: T){
  @transient private var v = sc.broadcast(_v)

  def update (newValue: T, blocking: Boolean = false): Unit={
    //删除RDD是否需要锁定
    v.unpersist(blocking)
    v = sc.broadcast(newValue)
  }

  def value: T = v.value

  private def writeObject(out: ObjectOutputStream) ={
    out.writeObject(v)
  }

  private def readObject(in: ObjectInputStream)={
    v = in.readObject().asInstanceOf[Broadcast[T]]
  }
}

object BroadcastWrapperTest{
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("SparkTC").master("local")
      .getOrCreate()

    val sc = spark.sparkContext
    val myBroadcast = BroadcastWrapper(sc,"bd")
    val data = Array(1, 2, 3, 4, 5)
    val rdd = sc.parallelize(data)

  }
}
