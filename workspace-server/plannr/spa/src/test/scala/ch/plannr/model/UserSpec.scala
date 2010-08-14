package ch.plannr.model

import org.specs.specification.BeforeAfter
import org.specs.Specification
import bootstrap.liftweb.Fixtures
import ch.plannr.common.persistence.Dataloader

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class UserSpec extends Specification with Dataloader {
  def fixture = Fixtures.load

  "when all users were loaded " should {

    val numberOfUsers = 1
    "have listcount of " + numberOfUsers in {
      var allUsers = User.findAll

      (allUsers.size) must beEqualTo(numberOfUsers)
    }
  }

  val user = new User
  user.firstname = "firstname"
  user.username = "username"
  user.lastname = "lastname"
  user.email = "email"

  val xml = user.toXml


  println(xml)

  "when toXml is invoked" should {
    "return value" in {
      (xml \\ "user" \\ "lastname").text must beEqualTo("lastname")
      (xml \\ "user" \\ "firstname").text must beEqualTo("firstname")
      (xml \\ "user" \\ "email").text must beEqualTo("raffi.schmid@gmail.com")
    }
  }
}