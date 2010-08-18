package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import collection.JavaConversions._
import net.liftweb.util.Props
import ch.plannr.model.{Team, User}
import ch.plannr.services.{TeamService, UserService}
import xml.Node
import net.liftweb.http._
import javax.validation.{ConstraintViolation, ConstraintViolationException}

object TeamWebservice extends RestHelper with RESTSupport {
  serve {
    // /webservices/team
    case r@Req("webservices" :: "team" :: "add" :: _, _, PostRequest) => {

      try {
        if (S.param("ownerId").isDefined) {
          val ownerId = S.param("ownerId").open_!.toLong

          val team = Team.fromXml(r.xml.open_!)
          val savedTeam = TeamService.addTeamToOwner(team, ownerId)

          savedTeam.toXml
        }
        else {
          xmlMessage("ownerId must be specified")
        }
      }
      catch {
        case ex: ConstraintViolationException =>
          val set = Set() ++ (asSet(ex.getConstraintViolations))
          xmlViolation(set)
        case ex: IllegalArgumentException =>
          xmlMessage(ex.getMessage)
      }
    }

    case r@Req("webservices" :: "team" :: _, _, GetRequest) => {

      try {
        if (S.param("ownerId").isDefined) {
          val ownerId = S.param("ownerId").open_!.toLong
          val user = User.findById(ownerId)
          val set = Set() ++ asSet(user.open_!.ownerOf)
          list2Xml(set.toArray: _*)
        }
        else {
          xmlMessage("ownerId must be specified")
        }
      }
      catch {
        case ex: ConstraintViolationException =>
          val set: Set[ConstraintViolation[_]] = Set() ++ (ex.getConstraintViolations)
          xmlViolation(set)
        case ex: IllegalArgumentException =>
          xmlMessage(ex.getMessage)
      }
    }
    case r@Req("webservices" :: "team" :: teamId, _, DeleteRequest) => {

      println("=======================")
      println(teamId)
      TeamService.delete(Team.findById(teamId.get(0).toLong).get)
      xmlMessage("")
    }

  }
  def list2Xml(list: Team*): Node = {
    <teams>
      {list.map {it: Team => it.toXml}}
    </teams>
  }
}