package ch.plannr.common

import ch.plannr.model.User
import org.specs.Specification

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class FullEqualitySpec extends Specification with FullEquality {
  val origA = new User()
  origA.username = "a"
  origA.firstname = "a"
  origA.email = "a"

  val equalA = new User()
  equalA.username = "a"
  equalA.firstname = "a"
  equalA.email = "a"

  val notEqualA = new User()
  notEqualA.username = "b"
  notEqualA.firstname = "a"
  notEqualA.email = "a"


  "fullEquality" should {
    "return true on two identical objects" in {
      fullEquality(origA, equalA) must beTrue

      (1) must beEqualTo(1)
    }
    "return false on two NOT identical objects" in {
      fullEquality(origA, notEqualA) must beFalse

      (1) must beEqualTo(1)
    }
  }
}