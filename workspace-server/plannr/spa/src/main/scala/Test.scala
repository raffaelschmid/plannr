import net.liftweb.common.Box
import net.liftweb.util.SimpleInjector

abstract class Thing
class TestThing extends Thing
class ProdThing extends Thing
object Injection extends SimpleInjector
trait Unboxing{
  implicit def unboxing[T](input:Box[T]):T = input.open_!
}

object Test extends Application with Unboxing{
  Injection.registerInjection[Thing]{()=>
    new TestThing
  }

  val myThing:Thing = Injection.inject[Thing]
  println(myThing.getClass.getName)
}