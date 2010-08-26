package ch.plannr.common.persistence

import java.lang.reflect.Field
import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JString
import ch.plannr.common.ToString
import xml.{NodeSeq, Text, Elem, Node}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait Domain { // extends ToString {

  def toXml = <unsupported/>

  def toJson = new JString("unsupported")

}
trait MetaDomain[T] {
  def fromXml(xml: NodeSeq): T
}