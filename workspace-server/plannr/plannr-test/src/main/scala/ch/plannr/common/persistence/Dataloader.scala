package ch.plannr.common.persistence

import org.specs.specification.BeforeAfter
import org.specs.Specification

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait Dataloader extends Specification with BeforeAfter {
  def fixtureStart = println("no fixtures configured")

  def fixtureEnd = println("no fixtures configured")
  
  doBeforeSpec {
    fixtureStart
  }

  doAfterSpec {
    fixtureEnd
  }
}