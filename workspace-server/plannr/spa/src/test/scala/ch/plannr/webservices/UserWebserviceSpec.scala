package ch.plannr.webservices

/**
 * User: Raffael Schmid
 *
 * Sorry for this Specification -> had problems to start/stop jetty server before and after each specification.
 * In this case it catchs the groove.
 */

import _root_.ch.plannr.testing.IntegrationTestPhase
import org.specs.Specification
import net.liftweb.http.testing._
import ch.plannr.model.User
import ch.plannr.common.Conversion

class UserWebserviceSpec extends Specification with IntegrationTestPhase with UserTestdata with Conversion{

  "POST to /webservices/login" should {
    "credentials match" in {
      val response:TestResponse = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "plannr"), Nil)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
      val user = User.fromXml(response.xml.open_! \\ "response" \\ "user")

      user.lastname must beEqualTo("Schmid")
      user.firstname must beEqualTo("Raffael")
      user.email must beEqualTo("raffael.schmid@plannr.ch")
    }

    "wrong password" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
      1 must beEqual(1)
    }

    "wrong username" in {
      val response: TestResponse = post("/webservices/login", buildBasicAuthClient("raffael.schmid@plannr.ch", "wrongpass"), Nil)
      response.!(401, "invalid credentials -> 401 should be returned but wasn't")
      1 must beEqual(1)
    }
  }


  "POST to /webservices/register" should {
    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register", invalidUser01.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@property").text) must beEqualTo("email")
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@message").text) must beEqualTo("not a well-formed email address")
    }


    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register?self=true", validUser01.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val user = User.fromXml(response.xml.open_! \\ "response" \\ "user")



      user.activationSalt mustNot beEqual(0)
      user.id mustNot beEqual(0)

      // because no transaction is closed during different tests->flush
      User.findById(user.id).open_!.removeAndFlush
    }

    "return fully registered user and send notification mail if user was registered by other" in {
      val response: TestResponse = post("/webservices/register", validUser01.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val user = User.fromXml(response.xml.open_! \\ "response" \\ "user")

      user.activationSalt mustNot beEqual(0)
      user.id mustNot beEqual(0)

      // because no transaction is closed during different tests->flush
      User.findById(user.id).open_!.removeAndFlush
    }

  }

  "GET to /webservices/validate/${userid}?salt=${salt}" should {
    "validate user if exist" in {

      //persist new user and flush because
      val userToValidate = validUser01.persistAndFlush

      val userid = userToValidate.id
      val salt = userToValidate.activationSalt

      val response: TestResponse = get("/webservices/validate/" + userid + "?salt=" + salt)
      response.!(200, "user with valid id and salt should be validated")

      val xml = response.xml.open_!
      ((xml \\ "response" \\ "@success").text.trim) must beEqualTo("true")

      // because no transaction is closed during different tests->flush
      userToValidate.removeAndFlush
    }
  }

  "POST to /webservices/update/${userid}" should {
    "save updated user" in {
      //save before update
      val alreadyPersistedUser = validUser01
      alreadyPersistedUser.persistAndFlush

      //now the test
      alreadyPersistedUser.firstname = "newfirstname"
      alreadyPersistedUser.lastname = "newlastname"
      alreadyPersistedUser.email = "new.webservice@plannr.ch"

      val url = "/webservices/update/" + alreadyPersistedUser.id.toString
      val response: TestResponse = post(url, alreadyPersistedUser.toXml)
      val updatedUser = User.fromXml(response.xml.open_! \\ "response" \\ "user")

      //and the assertions
      updatedUser.firstname must beEqual("newfirstname")
      updatedUser.lastname must beEqual("newlastname")
      updatedUser.email must beEqual("new.webservice@plannr.ch")

      //removing detached instance (call to webservices run in different EntityManager than the tests)
      User.findById(updatedUser.id).open_!.removeAndFlush
    }
  }

  "GET to /webservices/user/find?email=raffael.schmid@plannr.ch" should {
    "return error xml if instance is not valid" in {

      val response: TestResponse = get("/webservices/user/find?email=raffael.schmid@plannr.ch")
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val user = User.fromXml(response.xml.open_! \\ "response" \\ "user")
      user.firstname must beEqual("Raffael")
      user.lastname must beEqual("Schmid")
      user.email must beEqual("raffael.schmid@plannr.ch")
    }
  }
}

trait UserTestdata {
  def invalidUser01 = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice"
    user
  }

  def validUser01 = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice01@plannr.ch"
    user
  }

  def validUser02 = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.password = "webservice"
    user.email = "webservice-02@plannr.ch"
    user
  }
}

