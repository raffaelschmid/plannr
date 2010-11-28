package ch.plannr.common.di

import net.liftweb.util.SimpleInjector
import ch.plannr.common.Unboxing

object Context extends SimpleInjector with Unboxing{

  implicit def inject_![T](implicit man: Manifest[T]): T = inject(man)

  def put[T](f: () => T)(implicit man: Manifest[T]){
    registerInjection(f)(man)
  }
}