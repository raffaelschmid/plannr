package ch.plannr.common.webservice

import scala.xml.Node
import javax.validation.ConstraintViolation

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait RESTSupport {
  private def stringValue(o: Any): String = if (o != null) o.toString else ""

  private def getMessage(msg: String): Node = <message>
    {msg}
  </message>

  private def getError(error: String): Node = <error>
    {error}
  </error>

  private def getViolation(v: ConstraintViolation[_]): Node = {
      <violation property={stringValue(v.getPropertyPath)} message={stringValue(v.getMessage)} value={stringValue(v.getInvalidValue)}/>
  }

  def xmlSuccess = <response success="true"/>

  def xmlMessage(messages: String*) = {
    <response>
      <messages>
        {messages.map {getMessage}}
      </messages>
    </response>
  }

  def xmlError(errors: String*) = {
    <response>
      <errors>
        {errors.map {getError}}
      </errors>
    </response>
  }

  def xmlViolation(violations: Set[ConstraintViolation[_]]) = {
    <response>
      <violations>
        {violations.map {getViolation}}
      </violations>
    </response>
  }
}