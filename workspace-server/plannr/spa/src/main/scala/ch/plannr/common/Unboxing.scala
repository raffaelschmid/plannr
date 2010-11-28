package ch.plannr.common

import net.liftweb.common.Box

trait Unboxing{
  implicit def unboxing [T]( input : Box[T ]):T = input.open_!
}