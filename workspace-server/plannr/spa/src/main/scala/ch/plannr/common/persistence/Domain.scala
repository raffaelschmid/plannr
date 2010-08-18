package ch.plannr.common.persistence

import java.lang.reflect.Field
import net.liftweb.json.Extraction
import xml.{Text, Elem, Node}
import net.liftweb.json.JsonAST.JString
import ch.plannr.common.ToString

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait Domain{ // extends ToString {

  def toXml = <undefined/>
  def toJson = new JString("undefined")
 
}