package ch.plannr.webservices

/**
 * User: Raffael Schmid
 *
 * TODO
 */


import _root_.ch.plannr.testing.ServerIntegrationSpecification
import org.specs.Specification
import bootstrap.liftweb.Fixtures
import net.liftweb.http.testing._
import ch.plannr.model.User
import xml.NodeSeq

class UserWebserviceSpec extends Specification with ServerIntegrationSpecification with Testdata {
  def fixture = Fixtures.load

  "POST to /webservices/login" should {
    "credentials match" in {
      val response = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "plannr"), Nil)
      val xml = response.xml.open_!

      (xml \\ "user" \\ "lastname").text.trim must beEqualTo("Schmid")
      (xml \\ "user" \\ "firstname").text.trim must beEqualTo("Raffael")
      (xml \\ "user" \\ "email").text.trim must beEqualTo("raffael.schmid@plannr.ch")
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
    }

    "wrong password" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
    }

    "wrong username" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
    }
  }


  "POST to /webservices/register" should {
    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register", invalidUser.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      println(xml)
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@property").text) must beEqualTo("email")
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@message").text) must beEqualTo("not a well-formed email address")


    }


    "return fully registered user (valid user)" in {
      val inputXml = validUser.toXml
      println(inputXml)
      val response: TestResponse = post("/webservices/register", inputXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      println(xml)

      val userId = (xml \\ "user" \\ "id").text.trim.toLong
      userId mustNot beEqual(0)

      // because no transaction is closed during different tests
      User.findById(userId).open_!.removeAndFlush
    }

  }

  "GET to /webservices/validate/${userid}?salt=${salt}" should {
    "validate user if exist" in {

      //persist new user and flush because
      val userToValidate = validUser.persistAndFlush

      val userid = userToValidate.id
      val salt = userToValidate.activationSalt

      val url = "/webservices/validate/" + userid + "?salt=" + salt
      println("url to call: " + url)
      val response: TestResponse = get(url)
      response.!(200, "user with valid id and salt should be validated")

      val xml = response.xml.open_!
      ((xml \\ "response" \\ "@success").text.trim) must beEqualTo("true")
      (1) must beEqualTo(1)

      userToValidate.removeAndFlush
    }
  }

  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}
trait Testdata {
  def invalidUser = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice"
    user
  }

  def validUser = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice@plannr.ch"
    user
  }
}