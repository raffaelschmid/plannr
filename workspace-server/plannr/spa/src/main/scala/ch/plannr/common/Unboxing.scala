package ch.plannr.common

import actors.!
import net.liftweb.common.Box

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
trait Unboxing{
  implicit def unboxing [T]( input : Box[T ]):T = input.open_!
}