


abstract class Animal(name: String)

case class Dog(val name: String, wang: String) extends Animal(name)

case class Cat(val name: String, miao: String) extends Animal(name)

class Peple[Animal]() {

}

class Zoo[T <: Animal](implicit m: Manifest[T]) {

  def getNew(): Animal = {
    m.runtimeClass.getSimpleName match {
      case "Dog" => Dog("hung1", "wwww")
      case "Cat" => Cat("mimi", "miao ~~~")
    }
  }

}

object Test {
  def main(args: Array[String]): Unit = {
    val a = Dog("a", "s")

  }
}
