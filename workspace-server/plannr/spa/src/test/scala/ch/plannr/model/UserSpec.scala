package ch.plannr.model

import org.specs.specification.BeforeAfter
import org.specs.Specification
import bootstrap.liftweb.Fixtures
import ch.plannr.common.persistence.{DBModel, Dataloader}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class UserSpec extends Specification with Dataloader {
  def fixture = Fixtures.load

  "findAll on User" should {

    val numberOfUsers = 1
    "return all users from database" + numberOfUsers in {
      var allUsers = User.findAll

      (allUsers.size) mustNot beEqualTo(0)
    }
  }


  val xml = UserSpec.testuser.toXml

  "toXml" should {
    "return user as xml" in {
      (xml \\ "user" \\ "lastname").text.trim must beEqualTo("lastname")
      (xml \\ "user" \\ "firstname").text.trim must beEqualTo("firstname")
      (xml \\ "user" \\ "email").text.trim must beEqualTo("email")
    }
  }

  "fromXml" should {
    "return user as object" in {
      println(UserSpec.testuser.toXml)
      val user = User.fromXml(UserSpec.testuser.toXml)
      val clone = UserSpec.testuser
      println(user.address)
      println(clone.address)

      User.fullEquality(user, clone) must beTrue
    }
  }

  "newActivationSalt" should {
    "return random long number" in {
      val activationSalt = User.newActivationSalt
      activationSalt mustNot beEqual(0)
    }
  }


}
object UserSpec {
  def testuser = {
    val user = new User
    user.firstname = "firstname"
    user.lastname = "lastname"
    user.email = "email"
    user
  }
}