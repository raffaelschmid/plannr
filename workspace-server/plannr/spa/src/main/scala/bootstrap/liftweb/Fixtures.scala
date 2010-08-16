package bootstrap.liftweb

import net.liftweb.common.Loggable
import net.liftweb.util.Props
import Props.RunModes._
import javax.persistence.EntityTransaction
import ch.plannr.model.{Address, Role, User}
import ch.plannr.common.persistence.DBModel

/**
 * User: Raffael Schmid
 *
 * Load fixtures environment specific
 */
object Fixtures extends Loggable {

  def load() {
    logger.info("-----------------------------------------------------")
    logger.info("-- fixturing -> started")
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
    logger.info("-- fixturing -> ended")
    logger.info("-----------------------------------------------------")

  }

  def testFixtures: Unit = {
    var trx: EntityTransaction = null
    try {

      val user1: User = new User
      user1.firstname = "Raffael"
      user1.lastname = "Schmid"
      user1.password = "plannr"
      user1.email = "raffael.schmid@plannr.ch"
      user1.validated=true

      val address1: Address = new Address
      address1.street1 = "Bahnhofstrasse 56"
      address1.zip = 5430
      address1.city = "Wettingen"

      user1.address = address1


      val admin_role: Role = new Role
      admin_role.rolename = "administrator"
      admin_role.persist

      user1.roles.add(admin_role)
      user1.persist

      val list = User.findAll
      println("---------------all users----------------")
      println(list)
      
    }
    catch {
      case ex: Exception => {
        ex.printStackTrace()
        trx.rollback
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