package ch.plannr.common.webservice

import org.specs.Specification
import scala.xml.Utility._
import org.specs.runner.{JUnit, ScalaTest}
import net.liftweb.common.Loggable

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object Instance extends RESTSupport
class RESTSupportSpec extends Specification {



  "calls to xmlMessage"  should {
    val xml =  <response><messages><message>1st message</message></messages></response>
    "return " in {
      trim(Instance.xmlError("1st message")) must beEqualTo(trim(xml))
    }
  }
}