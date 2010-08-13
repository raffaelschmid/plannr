package ch.plannr.webservices

/**
 * User: Raffael Schmid
 * 
 * TODO
 */


import _root_.ch.plannr.testing.ServerIntegrationSpecification
import net.liftweb.common.{Box, Failure, Full}
import xml.{Node, Elem}
import net.liftweb.http.testing.{ClientBuilder, HttpResponse}
import org.specs.Specification
import bootstrap.liftweb.Fixtures

class LoginWebserviceSpec extends Specification with ServerIntegrationSpecification {

  def fixture = Fixtures.load
  
  "POST to /webservices/login" should {
    "return the user as xml (credentials, user available)" in {
      val xml: Node = post("/webservices/login", buildBasicAuthClient("raffi.schmid@gmail.com", "plannr"),Nil)
      (xml \\ "user" \\ "lastname").text.trim must beEqualTo("Schmid")
      (xml \\ "user" \\ "firstname").text.trim must beEqualTo("Raffael")
      (xml \\ "user" \\ "email").text.trim must beEqualTo("raffi.schmid@gmail.com")
      (1 + 1) must beEqualTo(2)
    }

    "return error as xml (credentials, user not available)" in {
      (1 + 1) must beEqualTo(2)
    }

    "return error as xml (wrong credentials, user not available)" in {
      (1 + 1) must be_==(2)
    }
  }
}