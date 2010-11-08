package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.model.{Team, User, Vacation}
import javax.validation.ConstraintViolationException
import scala.collection.JavaConversions._
import ch.plannr.services.VacationService
import net.liftweb.http.{GetRequest, S, Req, PostRequest}
import xml.Node

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object VacationWebservice extends RestHelper with RESTSupport {
  serve {
    // /webservices/vacation/add
    case r@Req("webservices" :: "vacation" :: _, _, GetRequest) => {
      try {
        if (User.currentUser.isDefined) {
          val ownerId = User.currentUser.open_!.id
          val user = User.findById(ownerId).open_!
          val list: List[Vacation] = user.vacations
          val retVal = list2VacationXml(list.toArray: _*)
          xmlSuccess(retVal)
        }
        else {
          xmlError("user not logged in")
        }
      }

    }

    // /webservices/vacation/add
    case r@Req("webservices" :: "vacation" :: "add" :: _, _, PostRequest) => {

      try {
        if (User.currentUser.isDefined) {
          val userId = User.currentUser.open_!.id

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
        else {
          xmlError("user not logged in")
        }
      }
      catch {
        case ex: ConstraintViolationException =>
          val set = Set() ++ (asSet(ex.getConstraintViolations))
          xmlViolation(set)
        case ex: IllegalArgumentException =>
          xmlError(ex.getMessage)
      }
    }
  }

  def list2VacationXml(list: Vacation*): Node = {
    <vacations>
      {list.map {it: Vacation => it.toXml}}
    </vacations>
  }
}