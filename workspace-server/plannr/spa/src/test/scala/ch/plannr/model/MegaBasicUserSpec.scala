package ch.plannr.model

import org.specs.Specification
import ch.plannr.common.Conversion
import javax.validation.ConstraintViolation

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
class MegaBasicUserSpec extends Specification with Conversion{
  "validation with a missing firstname" should {
     "return a set with one single FieldError" in {

       val user = User()
       val violations:Set[ConstraintViolation[User]] = user.validate
       (violations.size) mustBe 6
    }
  }
}