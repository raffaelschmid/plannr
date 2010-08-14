package ch.plannr.common.persistence

import java.lang.reflect.Field
import net.liftweb.json.Extraction
import xml.{Text, Elem, Node}
import net.liftweb.json.JsonAST.JString

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait Domain {

  def toXml = <undefined/>
  def toJson = new JString("undefined")
//  def toXml() = {
//    var notNullFields = getClass().getDeclaredFields().filter{
//      field: Field =>
//        field.setAccessible(true)
//        field.get(this)!=null
//    }
//    var childs: Array[Node] = notNullFields.map {
//      field: Field =>
//        field.setAccessible(true)
//        if (field.get(this).toString != null)
//          Elem(null, field.getName, scala.xml.Null, scala.xml.TopScope, Text(field.get(this).toString))
//        else null
//    }
//
//    Elem(null, getClass.getSimpleName, scala.xml.Null, scala.xml.TopScope, childs: _*)
//  }

  override def toString() =
    {
      this.getClass.getSimpleName + " [" +
              getClass().getDeclaredFields().map {
                field: Field =>
                  field.setAccessible(true)
                  field.getName() + " = " + field.get(this)
              }.mkString(", ") + "]"
    }
}