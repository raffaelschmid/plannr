package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.model.{Team, User, Vacation}
import scala.collection.JavaConversions._
import ch.plannr.services.VacationService
import xml.Node
import net.liftweb.http._
import javax.validation.{ConstraintViolation, ConstraintViolationException}
import ch.plannr.common.Conversion

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object VacationWebservice extends RestHelper with RESTSupport with Conversion {
  serve {
    //PUT: /webservices/vacation
    case r@Req("webservices" :: "vacation" :: vacationId, _, DeleteRequest) => {
      try {
        val vacation = Vacation.findById(vacationId.head.toLong)
        vacation.open_!.remove
        xmlSuccess
      } catch {
        case ex: Exception => xmlError(ex.toString)
      }
    }
    //PUT: /webservices/vacation
    case r@Req("webservices" :: "vacation" :: _, _, PutRequest) => {
      val vacation = Vacation.fromXml(r.xml.open_!)
      try {

        val vacationToUpdate = Vacation.findById(vacation.id).open_!
        if (S.param("teamId").isDefined) {
          val teamId = S.param("teamId").open_!.toLong
          val team = Team.findById(teamId).open_!
          vacationToUpdate.team = team
        }
        vacationToUpdate.title = vacation.title
        vacationToUpdate.description = vacation.description
        vacationToUpdate.from = vacation.from
        vacationToUpdate.to = vacation.to
        vacationToUpdate.persist
        vacationToUpdate.toXml
      }
      catch {
        case ex: ConstraintViolationException => {
          println("constraint violation")
          xmlViolation(ex.getConstraintViolations)
        }
      }
    }
    //POST: /webservices/vacation/add
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
      {list.map {
      it: Vacation => it.toXml
    }}
    </vacations>
  }
}