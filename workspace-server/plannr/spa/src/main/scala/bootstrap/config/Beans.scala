package bootstrap.config

import ch.plannr.common.di.{Context}
import ch.plannr.services._
import ch.plannr.model.User
import ch.plannr.webservices._

/**
 * User: Raffael Schmid
 *
 * TODO
 */

object Beans {
  lazy val env = System.getProperty("run.mode")
  def init = {
    env match {
      case "test" =>
        Context.put[TeamService] {() => TeamServiceImpl}
        Context.put[VacationService] {() => VacationServiceImpl}
        Context.put[SearchService] {() => SearchServiceImpl}
        Context.put[SecurityService] {() => MockSecurityService}
        Context.put[TeamWebservice] {() => TeamWebserviceImpl}
        Context.put[VacationWebservice] {() => VacationWebserviceImpl}
        Context.put[SearchWebservice] {() => SearchWebserviceImpl}

      case _ =>
        Context.put[TeamService] {() => TeamServiceImpl}
        Context.put[VacationService] {() => VacationServiceImpl}
        Context.put[SearchService] {() => SearchServiceImpl}
        Context.put[SecurityService] {() => SecurityServiceImpl}
        Context.put[TeamWebservice] {() => TeamWebserviceImpl}
        Context.put[VacationWebservice] {() => VacationWebserviceImpl}
        Context.put[SearchWebservice] {() => SearchWebserviceImpl}

    }
  }
}

object MockSecurityService extends SecurityService {
  def isUserLoggedIn: Boolean = true

  def currentUser = {
    val user = User
    user.id = 1
    user.firstname = "Test"
    user.lastname = "Test"
    user
  }
}