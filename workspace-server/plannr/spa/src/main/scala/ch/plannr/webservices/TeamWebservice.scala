package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import collection.JavaConversions._
import net.liftweb.util.Props
import ch.plannr.model.{Team, User}
import ch.plannr.services.{TeamService, UserService}
import net.liftweb.http._
import javax.validation.{ConstraintViolation, ConstraintViolationException}
import xml.{NodeSeq, Node}

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
          xmlError("ownerId must be specified")
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

    case r@Req("webservices" :: "team" :: _, _, GetRequest) => {

      try {
        if (S.param("ownerId").isDefined) {
          val ownerId = S.param("ownerId").open_!.toLong
          val user = User.findById(ownerId)
          val set = asSet(user.open_!.ownerOf)
          list2TeamsXml(set.toArray: _*)
        }
        else {
          xmlError("ownerId must be specified")
        }
      }
      catch {
        case ex: ConstraintViolationException =>
          val set: Set[ConstraintViolation[_]] = Set() ++ (ex.getConstraintViolations)
          xmlViolation(set)
        case ex: IllegalArgumentException =>
          xmlError(ex.getMessage)
      }
    }

    case r@Req("webservices" :: "team" :: "member" :: memberId, _, DeleteRequest) => {

      val teamId: Long = S.param("teamId").open_!.toLong


      val newMembers = TeamService.deleteUserFromTeam(teamId, memberId(0).toLong)

      list2UsersXml(newMembers: _*)

    }

    case r@Req("webservices" :: "team" :: teamId, _, DeleteRequest) => {

      try {
        TeamService.delete(Team.findById(teamId(0).toLong).get)
        xmlSuccess
      } catch {
        case ex: Exception => xmlError(ex.getMessage)
      }
    }
    case r@Req("webservices" :: "team" :: "member" :: _, _, PostRequest) => {


      try {


        val teamId = S.param("teamId").open_!.toLong
        val users: List[User] = r.xml.open_!

        val newMembers = TeamService.addUsersToTeam(teamId, users)

        list2UsersXml(newMembers: _*)
      }
      catch {
        case ex: Exception => xmlError(ex.getMessage)
      }
    }
  }
  implicit def node2ListOfUsers(xml: NodeSeq): List[User] = {
    List((xml \\ "users" \\ "user").map {User.fromXml}: _*).sortWith((a, b) => a.id < b.id)
  }

  def list2TeamsXml(list: Team*): Node = {
    <teams>
      {list.map {it: Team => it.toXml}}
    </teams>
  }

  def list2UsersXml(list: User*): Node = {
    <users>
      {list.map {it: User => it.toXml}}
    </users>
  }
}