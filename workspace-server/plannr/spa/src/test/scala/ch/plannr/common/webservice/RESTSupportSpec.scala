package ch.plannr.common.webservice

import org.specs.Specification
import scala.xml.Utility._
import org.specs.runner.{JUnit, ScalaTest}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class RESTSupportSpec extends Specification with RESTSupport{

  "calls to xmlMessage"  should {
    val xml =  <response><messages><message>1st message</message></messages></response>
    "return " in {
      trim(xmlError("1st message")) must beEqualTo(trim(xml))
    }
  }
}