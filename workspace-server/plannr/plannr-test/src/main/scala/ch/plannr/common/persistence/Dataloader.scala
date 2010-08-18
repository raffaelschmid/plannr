package ch.plannr.common.persistence

import org.specs.specification.BeforeAfter
import org.specs.Specification

/**
 * User: Raffael Schmid
 *
 * a poor solution for the dataloader only run once per tests
 */
object RunOnce {
  var shouldRun = true
}

trait Dataloader extends Specification with BeforeAfter {

  def fixture
  doBeforeSpec {
    if (RunOnce.shouldRun) {
      fixture
      RunOnce.shouldRun = false
    }
  }
}