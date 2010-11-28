package ch.plannr.common.persistence

import org.scala_libs.jpa.{ThreadLocalEM, LocalEMF}
import javax.validation.Validation
import javax.persistence.Transient

object DBModel extends LocalEMF("jpaweb", false) with ThreadLocalEM {
}

trait Persistent[T] {
  self: T =>

  @Transient
  private val validatorFactory = Validation.buildDefaultValidatorFactory();

  @Transient
  private val validator = validatorFactory.getValidator();

  def validate = {
    validator.validate(this)
  }


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
