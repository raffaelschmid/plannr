package ch.plannr.common

import org.specs.Specification
import javax.validation.ConstraintViolation
import ch.plannr.model.User
import xml.NodeSeq

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
class MessageDisplaySpec extends Specification with MessageDisplay with Conversion{
  "validation with a missing firstname" should {
     "return a set with one single FieldError" in {

       val user = User()
       val violations:java.util.Set[ConstraintViolation[User]] = user.validate

       violations.size mustBe 6

       val html:NodeSeq = violations
       println(html)

       //assertions

    }
  }
}