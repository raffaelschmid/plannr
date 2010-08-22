package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import net.liftweb.http.{S, Req, PostRequest}
import ch.plannr.model.{Team, User, Vacation}
import javax.validation.ConstraintViolationException
import scala.collection.JavaConversions._
import ch.plannr.services.VacationService

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object VacationWebservice extends RestHelper with RESTSupport {
  serve {
    // /webservices/vacation/add
    case r@Req("webservices" :: "vacation" :: "add" :: _, _, PostRequest) => {

      try {

        val userId = S.param("userId").open_!.toLong
        val teamId = S.param("teamId").open_!.toLong
        val user = User.findById(userId).open_!
        val team = Team.findById(teamId).open_!


        //vacation together with user and team
        val vacation = Vacation.fromXml(r.xml.open_!)
        vacation.team = team
        vacation.user = user

        VacationService.save(vacation)

        vacation.toXml
      }
      catch {
        case ex: ConstraintViolationException =>
          val set = Set() ++ (asSet(ex.getConstraintViolations))
          xmlViolation(set)
        case ex: IllegalArgumentException =>
          xmlMessage(ex.getMessage)
      }
    }
  }
}