package ch.plannr.common

import java.lang.reflect.Field

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait FullEquality {
  def fullEquality(o1: AnyRef, o2: AnyRef): Boolean = {
    if (o1 == null && o2 == null)
      true
    else if (o1 != null && o2 != null) {
      val allFields: Array[Field] = o1.getClass.getDeclaredFields
      val unequalFields = allFields.filter {
        it: Field =>
          it.setAccessible(true);
          it.get(o1) != it.get(o2)
      }
      return unequalFields.size == 0
    }
    else
      false
  }
}