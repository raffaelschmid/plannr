package ch.plannr.common

import net.liftweb.util.FieldError
import javax.validation.ConstraintViolation
import xml.NodeSeq

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait MessageDisplay extends Conversion{
  implicit def constraintsToFieldErrorsList[T](set: java.util.Set[ConstraintViolation[T]]): NodeSeq = {
    val list:Set[ConstraintViolation[T]] = set
    <ul>
      {list.map(cv => <li>{cv.getPropertyPath.toString.capitalize} {cv.getMessage}</li>)}
    </ul>
  }

}