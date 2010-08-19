package ch.plannr.webservices

import xml.NodeSeq
import ch.plannr.model.Team
import ch.plannr.model.User
import net.liftweb.http.testing.{ReportFailure, TestResponse}
import org.specs.Specification
import ch.plannr.testing.IntegrationTestPhase
import ch.plannr.common.Conversion

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class TeamWebserviceSpec extends Specification with IntegrationTestPhase with TeamTestdata with Conversion {
  "POST to /webservices/team" should {
    "return error xml if instance is not valid" in {

      val response: TestResponse = post("/webservices/team/add?ownerId=1", invalidTeam.toXml)
      response.!(200, "http response code must be 200")

      val xml = response.xml.open_!
      val violations: NodeSeq = (xml \\ "response" \\ "violations" \\ "violation")
      (violations(0) \ "@property").text.trim must beIn(List("name", "description"))
      (violations(1) \ "@property").text.trim must beIn(List("name", "description"))
    }
    "return error xml if instance is not valid" in {
      val response: TestResponse = post("/webservices/team/add?ownerId=1", validTeam.toXml)
      response.!(200, "http response code must be 200")

      val teams: List[Team] = get("/webservices/team?ownerId=1").xml.open_!
      teams(0).id must beEqual(1)
      teams(1).id must beEqual(2)
    }
    "delete team with id=2" in {
      val response: TestResponse = delete("/webservices/team/2")
      response.!(200, "http response code must be 200")

      val teams = get("/webservices/team=ownerId=1").xml.open_!
      teams.size must beEqual(1)
    }
    "add member to team, check, and delete afterwards" in {
      val userA = new User()
      userA.firstname = "a" * 10
      userA.lastname = "a" * 10
      userA.email = "aa@aaa.aa"
      userA.password = "a" * 10

      val userB = new User()
      userB.firstname = "b" * 10
      userB.lastname = "b" * 10
      userB.email = "bb@bbb.bb"
      userB.password = "b" * 10


      val addResponse: TestResponse = post("/webservices/team/member?teamId=1", listOfUsersToNode(List(userA, userB)))
      val users: List[User] = addResponse.xml.open_!

      users(0).firstname must beEqual(userA.firstname)
      users(1).firstname must beEqual(userB.firstname)
      users.size must beEqual(2)



      val delResponse: TestResponse = delete("/webservices/team/member/" + users(1).id + "?teamId=1")
      val usersAfterDelete: List[User] = delResponse.xml.open_!
      (usersAfterDelete.size) must beEqual(1)

    }

  }

  implicit def node2ListOfTeams(xml: NodeSeq): List[Team] = {
    List((xml \\ "teams" \\ "team").map {Team.fromXml}: _*).sortWith((a, b) => a.id < b.id)
  }

  implicit def node2ListOfUsers(xml: NodeSeq): List[User] = {
    List((xml \\ "users" \\ "user").map {User.fromXml}: _*).sortWith((a, b) => a.id < b.id)
  }

  implicit def listOfUsersToNode(users: List[User]): NodeSeq = {
    <users>
      {users.map(user => user.toXml)}
    </users>
  }

}

trait TeamTestdata {
  def invalidTeam = {
    val team = new Team
    team.name = "name" //insufficient characters (min:5)
    team.description = "description" //insufficient characters(min:20)
    team
  }

  def validTeam = {
    val team = new Team
    team.name = "namenamename"
    team.description = "descriptiondescription"
    team
  }
}