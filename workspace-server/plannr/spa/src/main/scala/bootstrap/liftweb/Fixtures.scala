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

      val author1: User = new User
      author1.username = "schmidic"
      author1.firstname = "Raffael"
      author1.lastname = "Schmid"
      author1.password = "plannr"
      author1.email = "raffi.schmid@gmail.com"
      author1.validated=true

      val address: Address = new Address
      address.street1 = "Bahnhofstrasse 56"
      address.zip = 5430
      address.city = "Wettingen"

      author1.address = address


      val admin: Role = new Role
      admin.rolename = "administrator"
      admin.persist

      author1.roles.add(admin)
      author1.persist

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