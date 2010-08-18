package bootstrap.liftweb

import net.liftweb.common.Loggable
import net.liftweb.util.Props
import Props.RunModes._
import javax.persistence.EntityTransaction
import ch.plannr.common.persistence.DBModel
import ch.plannr.model.{Team, Address, Role, User}
import ch.plannr.services.UserService

/**
 * User: Raffael Schmid
 *
 * Load fixtures environment specific
 */
object Fixtures extends Loggable {
  def load() {
    logger.info("-----------------------------------------------------")
    logger.info("-- fixturing -> started in mode: " + Props.modeName)
    logger.info("-----------------------------------------------------")
    Props.mode match {
      case Development => {
        developmentFixtures
      }
      case Test => {
        testFixtures
      }
      case Production => {
        productionFixtures
      }
      case _ => logger.info("no runmode selected -> load nothing")
    }
    logger.info("-----------------------------------------------------")
    logger.info("-- fixturing -> ended in mode: " + Props.modeName)
    logger.info("-----------------------------------------------------")

  }

  def testFixtures: Unit = {
    try {

    }
    catch {
      case ex: Exception => {
        ex.printStackTrace()
      }
    } finally {

    }

  }

  def developmentFixtures: Unit = testFixtures

  def productionFixtures: Unit = {
    logger.info("production mode -> load nothing")
  }

  def nonefixtures(): Unit = logger.info("no runmode selected -> load nothing")
}