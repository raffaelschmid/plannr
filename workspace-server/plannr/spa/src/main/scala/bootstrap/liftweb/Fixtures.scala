package bootstrap.liftweb

import net.liftweb.common.Loggable
import net.liftweb.util.Props
import Props.RunModes._
import ch.plannr.common.persistence.Model
import javax.persistence.{EntityTransaction, EntityManager, Persistence, EntityManagerFactory}
import ch.plannr.model.{Role, User}

/**
 * User: Raffael Schmid
 *
 * Load fixtures environment specific
 */
object Fixtures extends Loggable {
  var emf: EntityManagerFactory = _

  def load() {
    var em: EntityManager = null;
    var tx: EntityTransaction = null
    try {
      em = Model.openEM
      tx = em.getTransaction

      logger.info("-----------------------------------------------------")
      logger.info("-- fixturing -> started")
      logger.info("-----------------------------------------------------")
      Props.mode match {
        case Development => {
          developmentFixtures(em)
        }
        case Test => {
          testFixtures(em)
        }
        case Production => {
          productionFixtures(em)
        }
        case _ => logger.info("no runmode selected -> load nothing")
      }
      logger.info("-----------------------------------------------------")
      logger.info("-- fixturing -> ended")
      logger.info("-----------------------------------------------------")

      tx.commit
    }
    catch {
      case ex: Exception => {
        ex.printStackTrace()
        tx.rollback
      }
    } finally {
      if (em != null) em.close()
    }
  }

  def testFixtures(em: EntityManager): Unit = {
    val author1: User = new User
    author1.username = "schmidic"
    author1.firstname="Raffael"
    author1.lastname="Schmid"

    val admin: Role = new Role
    admin.rolename = "administrator"
    em.persist(admin)
    author1.roles.add(admin)

    em.persist(author1)

  }

  def developmentFixtures(em: EntityManager): Unit = testFixtures(em)

  def productionFixtures(em: EntityManager): Unit = {logger.info("production mode -> load nothing")}

  def nonefixtures(em: EntityManager): Unit = logger.info("no runmode selected -> load nothing")
}