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
import ch.plannr.common.persistence.DBModel
import ch.plannr.model.User

class UserWebserviceSpec extends Specification with ServerIntegrationSpecification {
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

  //    "POST to /webservices/register" should {
  //    "return fully registered user" in {
  //      val response: TestResponse = post("/webservices/register", <user>
  //        <username>plannrtest</username> <password>plannrtest</password> <firstname>FN</firstname> <lastname>LN</lastname> <email>plannrtest@plannr.ch</email>
  //      </user>)
  //
  //
  //      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
  //
  //      val xml = response.xml.open_!
  //      (xml \\ "user" \\ "id").text.trim.toLong mustNot beEqual(0)
  //
  //
  //    }
  //  }

  "POST to /webservices/register" should {
    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register", UserWebserviceSpec.invalid_user.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      println(xml)
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@property").text) must beEqualTo("email")
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@message").text) must beEqualTo("not a well-formed email address")


    }


    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register", UserWebserviceSpec.valid_user.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      println(xml)

      val userId = (xml \\ "user" \\ "id").text.trim.toLong
      userId mustNot beEqual(0)

      // because no transaction is closed during different tests
      User.findById(userId).open_!.removeAndFlush


    }
  }
  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}
object UserWebserviceSpec {
  def invalid_user = {
    val user = new User
    user.username = "webservice"
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice"
    user
  }

  def valid_user = {
    val user = new User
    user.username = "webservice"
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice@plannr.ch"
    user
  }
}