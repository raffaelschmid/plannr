package ch.plannr.common.webservice

import scala.xml.Node

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait RESTSupport {
  def getMessage(msg: String): Node = <message>
    {msg}
  </message>

  def getError(error: String): Node = <error>
    {error}
  </error>


  def replyXmlMessages(messages: String*) = {
    <response>
      <messages>
        {messages.map {getMessage}}
      </messages>
    </response>
  }

  def replyXmlErrors(errors: String*) = {
    <response>
      <errors>
        {errors.map {getError}}
      </errors>
    </response>
  }
}