package ch.plannr.common

import java.lang.reflect.Field

trait ToString {
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