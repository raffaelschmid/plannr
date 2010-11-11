package bootstrap.config

import ch.plannr.common.di.{Context}
import ch.plannr.services._
import ch.plannr.model.User

/**
 * User: Raffael Schmid
 *
 * TODO
 */

object Beans {
  def init = {
    System.getProperty("run.mode") match {
      case "test" =>
        Context.registerInjection[TeamService]{()=>TeamServiceImpl}
        Context.registerInjection[VacationService]{()=>VacationServiceImpl}
        Context.registerInjection[SearchService]{()=>SearchServiceImpl}
        Context.registerInjection[SecurityService]{()=>MockSecurityService}

      case _ =>
        Context.registerInjection[TeamService]{()=>TeamServiceImpl}
        Context.registerInjection[VacationService]{()=>VacationServiceImpl}
        Context.registerInjection[SearchService]{()=>SearchServiceImpl}
        Context.registerInjection[SecurityService]{()=>SecurityServiceImpl}

    }


  }


}

object MockSecurityService extends SecurityService{
  def isUserLoggedIn:Boolean = true
  def currentUser = {
    val user = User
    user.id=1
    user.firstname="Test"
    user.lastname="Test"
    user
  }
}