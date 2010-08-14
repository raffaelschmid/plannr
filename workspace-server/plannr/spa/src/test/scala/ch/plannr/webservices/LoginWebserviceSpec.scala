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
    "credentials match" in {
      val response = post("/webservices/login", buildBasicAuthClient("schmidic", "plannr"), Nil)
      val xml = response.xml.open_!

      (xml \\ "user" \\ "lastname").text.trim must beEqualTo("Schmid")
      (xml \\ "user" \\ "firstname").text.trim must beEqualTo("Raffael")
      (xml \\ "user" \\ "email").text.trim must beEqualTo("raffi.schmid@gmail.com")
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
    }

    "wrong password" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("schmidic", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
    }

    "wrong username" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("schmidic", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
    }
  }

  "POST to /webservices/register" should {
    "return fully registered user" in {
      val response: TestResponse = post("/webservices/register", <user>
        <username>hansi</username> <firstname>FN</firstname> <lastname>LN</lastname> <email>raffi.schmid@gmail.com</email>
      </user>)

      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
    }
  }


  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}