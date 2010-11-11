package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.model.{Team, User}
import net.liftweb.http._
import javax.validation.ConstraintViolationException
import xml.{NodeSeq, Node}
import ch.plannr.common.Conversion
import ch.plannr.common.di.Context
import ch.plannr.services.{SecurityService, TeamService}

object TeamWebservice extends RestHelper with RESTSupport with Conversion {

  val teamService = Context.inject_![TeamService]
  val securityService = Context.inject_![SecurityService]

  serve {

    /*
   * CRUD MEMBERS
   */
    case r@Req("webservices" :: "team" :: "member" :: memberId, _, DeleteRequest) => {

      val teamId: Long = S.param("teamId").open_!.toLong


      val newMembers = teamService.deleteUserFromTeam(teamId, memberId(0).toLong)

      xmlSuccess(list2UsersXml(newMembers: _*))

    }

    case r@Req("webservices" :: "team" :: "member" :: _, _, PostRequest) => {


      try {


        val teamId = S.param("teamId").open_!.toLong
        val users: List[User] = r.xml.open_!

        val newMembers = teamService.addUsersToTeam(teamId, users)

        xmlSuccess(list2UsersXml(newMembers: _*))
      }
      catch {
        case ex: Exception => xmlError(ex.getMessage)
      }
    }


    /*
    * CRUD TEAM
    */
    case r@Req("webservices" :: "team" :: _, _, GetRequest) => {

      try {
        if (securityService.isUserLoggedIn) {
          val ownerId = securityService.currentUser.id
          val user = User.findById(ownerId).open_!
          val list: List[Team] = user.ownerOf
          val retVal = list2TeamsXml(list.toArray: _*)
          xmlSuccess(retVal)
        }
        else {
          xmlError("user not logged in")
        }
      }
      catch {
        case ex: IllegalArgumentException =>
          xmlError(ex.getMessage)
      }
    }

    case r@Req("webservices" :: "team" :: _, _, PostRequest) => {

      try {
        if (securityService.isUserLoggedIn) {
          val ownerId = securityService.currentUser.id

          val team = Team.fromXml(r.xml.open_!)
          val savedTeam = teamService.addTeamToOwner(team, ownerId)

          xmlSuccess(savedTeam.toXml)
        }
        else {
          xmlError("user not logged in")
        }
      }
      catch {
        case ex: ConstraintViolationException =>
          xmlViolation(ex.getConstraintViolations)
        case ex: IllegalArgumentException =>
          xmlError(ex.getMessage)
      }
    }

    // TODO test that user is owner of team
    case r@Req("webservices" :: "team" :: _, _, PutRequest) => {

      try {
        val team = Team.fromXml(r.xml.open_!)
        teamService.update(team)
        xmlSuccess
      } catch {
        case ex: Exception => xmlError(ex.getMessage)
      }
    }


    // TODO test that user is owner of team
    case r@Req("webservices" :: "team" :: teamId, _, DeleteRequest) => {

      try {
        teamService.delete(Team.findById(teamId(0).toLong).get)
        xmlSuccess
      } catch {
        case ex: Exception => xmlError(ex.getMessage)
      }
    }

  }
  implicit def node2ListOfUsers(xml: NodeSeq): List[User] = {
    List((xml \\ "users" \\ "user").map {User.fromXml}: _*).sortWith((a, b) => a.id < b.id)
  }

  def list2TeamsXml(list: Team*): Node = {
    <teams>
      {list.map {
      it: Team =>
        println(it.members)
        it.toXml
    }}
    </teams>
  }

  def list2UsersXml(list: User*): Node = {
    <users>
      {list.map {it: User => it.toXml}}
    </users>
  }
}