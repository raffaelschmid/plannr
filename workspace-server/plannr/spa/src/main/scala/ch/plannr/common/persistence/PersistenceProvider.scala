package ch.plannr.common.persistence

/**
 * User: Raffael Schmid
 *
 * TODO
 */


import org.scala_libs.jpa.{ThreadLocalEM, LocalEMF}

object DBModel extends LocalEMF("jpaweb", false) with ThreadLocalEM{
}

trait Persistent {
  def persist = DBModel.persist(this)
  def merge = DBModel.merge(this)
  def remove = DBModel.remove(this)
  def removeAndFlush = DBModel.removeAndFlush(this)
}
