package ch.plannr.model

import java.lang.reflect.Field

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait
ToString {
  override def toString() = {
    this.getClass.getSimpleName + " [" +
            getClass().getDeclaredFields().map {
              field: Field =>
                field.setAccessible(true)
                field.getName() + " = " + field.get(this)
            }.mkString(", ") + "]"
  }
}

