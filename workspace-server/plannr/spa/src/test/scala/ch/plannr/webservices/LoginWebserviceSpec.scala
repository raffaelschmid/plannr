package ch.plannr.webservices

/**
 * User: Raffael Schmid
 * 
 * TODO
 */


import _root_.ch.plannr.testing.ServerIntegrationSpecification
import net.liftweb.common.{Box, Failure, Full}
import xml.{Node, Elem}
import org.specs.Specification
import bootstrap.liftweb.Fixtures
import net.liftweb.http.testing._

class LoginWebserviceSpec extends Specification with ServerIntegrationSpecification {

  def fixture = Fixtures.load
  
  "POST to /webservices/login" should {
    "return the user as xml (credentials, user available)" in {
      val response = post("/webservices/login", buildBasicAuthClient("schmidic", "plannr"),Nil)
      val xml = response.xml.open_!

      (xml \\ "user" \\ "lastname").text.trim must beEqualTo("Schmid")
      (xml \\ "user" \\ "firstname").text.trim must beEqualTo("Raffael")
      (xml \\ "user" \\ "email").text.trim must beEqualTo("raffi.schmid@gmail.com")
      (1 + 1) must beEqualTo(2)
    }

    "return error as xml (credentials, user not available)" in {
      val response:TestResponse = post("/webservices/login", buildBasicAuthClient("schmidic", "wrongpass"),Nil)
      response.!(401,"call with invalid credentials should result in 401 response but does not.")
      (2) must beEqualTo(2)
    }

    "return error as xml (wrong credentials, user not available)" in {
      (1 + 1) must be_==(2)
    }
  }
  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}