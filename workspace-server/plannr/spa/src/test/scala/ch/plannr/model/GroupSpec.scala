package ch.plannr.model

import org.specs.Specification
import bootstrap.liftweb.Fixtures
import org.specs.specification.BeforeAfter
import ch.plannr.common.persistence.Dataloader

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
class GroupSpec extends Specification with Dataloader {

  def fixture = Fixtures.load
  
  "when all groups were loaded " should {

    val numberOfUsers = 1
    "have listcount of " + numberOfUsers in {


      (1) must beEqualTo(1)
    }
  }
}