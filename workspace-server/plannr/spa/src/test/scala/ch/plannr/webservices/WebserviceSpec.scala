package ch.plannr.webservices

/**
 * User: Raffael Schmid
 *
 * Sorry for this Specification -> had problems to start/stop jetty server before and after each specification.
 * In this case it catchs the groove.
 */


import _root_.ch.plannr.testing.IntegrationTestPhase
import org.specs.Specification
import bootstrap.liftweb.Fixtures
import net.liftweb.http.testing._
import ch.plannr.common.persistence.Dataloader
import ch.plannr.model.{Team, User}
import xml.NodeSeq

class WebserviceSpec extends Specification with IntegrationTestPhase with Dataloader with UserTestdata with TeamTestdata {
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
      val response: TestResponse = post("/webservices/register", invalidUser.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@property").text) must beEqualTo("email")
      ((xml \\ "response" \\ "violations" \\ "violation" \\ "@message").text) must beEqualTo("not a well-formed email address")
    }


    "return fully registered user (valid user)" in {
      val response: TestResponse = post("/webservices/register", validUser.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      val userId = (xml \\ "user" \\ "id").text.trim.toLong
      val activationSalt = (xml \\ "user" \\ "activation_salt").text.trim.toLong

      activationSalt mustNot beEqual(0)
      println("activationsalt:" + activationSalt)
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

  "POST to /webservices/update/${userid}" should {
    "save updated user" in {
      //save before update
      val alreadyPersistedUser = validUser
      alreadyPersistedUser.persistAndFlush

      //now the test
      alreadyPersistedUser.firstname = "newfirstname"
      alreadyPersistedUser.lastname = "newlastname"
      alreadyPersistedUser.email = "new.webservice@plannr.ch"

      val url = "/webservices/update/" + alreadyPersistedUser.id.toString
      val response: TestResponse = post(url, alreadyPersistedUser.toXml)
      val updatedUser = User.fromXml(response.xml.open_!)

      //and the assertions
      updatedUser.firstname must beEqual("newfirstname")
      updatedUser.lastname must beEqual("newlastname")
      updatedUser.email must beEqual("new.webservice@plannr.ch")

      //removing detached instance (call to webservices run in different EntityManager than the tests)
      User.findById(updatedUser.id).open_!.removeAndFlush
    }
  }



  "POST to /webservices/team" should {
    "return error xml if instance is not valid" in {

      val response: TestResponse = post("/webservices/team", invalidTeam.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      val violations: NodeSeq = (xml \\ "response" \\ "violations" \\ "violation")
      (violations(0) \ "@property").text.trim must beIn(List("name","description"))
      (violations(1) \ "@property").text.trim must beIn(List("name","description"))
    }

    "return team as xml if input is valid" in {
      val response: TestResponse = post("/webservices/team", validTeam.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")
      val xml = response.xml.open_!

      (xml \\ "team" \\ "id").text.trim.toLong mustNot beEqualTo(0)
      (xml \\ "team" \\ "name").text.trim must beEqualTo("namename")
      (xml \\ "team" \\ "description").text.trim must beEqualTo("descriptiondescription")

    }
  }


  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}

trait UserTestdata {
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

trait TeamTestdata {
  def invalidTeam = {
    val team = new Team
    team.name = "name" //insufficient characters (min:5)
    team.description = "description" //insufficient characters(min:20)
    team
  }

  def validTeam = {
    val team = new Team
    team.name = "namename"
    team.description = "descriptiondescription"
    team
  }
}