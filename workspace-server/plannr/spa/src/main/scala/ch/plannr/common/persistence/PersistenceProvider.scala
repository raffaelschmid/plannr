package ch.plannr.common.persistence

/**
 * User: Raffael Schmid
 *
 * TODO
 */


import org.scala_libs.jpa.{ThreadLocalEM, LocalEMF}

object DBModel extends LocalEMF("jpaweb", false) with ThreadLocalEM {
}

trait Persistent[T] {
  self: T =>

  def persist = {
    DBModel.persist(this)
    
    this
  }

  def persistAndFlush = {
    DBModel.persistAndFlush(this)
    this
  }

  def merge: T = {
    DBModel.merge(this)
    this
  }

  def mergeAndFlush: T = {
    DBModel.mergeAndFlush(this)
    this
  }

  def remove = {
    DBModel.remove(this)
    true
  }

  def removeAndFlush = {
    DBModel.removeAndFlush(this)
    true
  }

  def flush = {
    DBModel.flush
    true
  }
}
