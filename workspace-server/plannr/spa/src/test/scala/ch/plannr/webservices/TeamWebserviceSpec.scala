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
    "return error -> invalid team" in {
      val response: TestResponse = post("/webservices/team?ownerId=1", invalidTeam.toXml)
      response.!(200, "http response code must be 200")

      val xml = response.xml.open_!
      println(xml)
      val violations: NodeSeq = (xml \\ "response" \\ "violations" \\ "violation")
      println("********************************************************************************************************************************************************************************************************")
      println((violations(0) \ "@property").text.trim)
      (violations(0) \ "@property").text.trim must beEqual("name")
    }
    "save valid instance and update afterwards" in {

      //save new team
      val postResponse: TestResponse = post("/webservices/team?ownerId=1", validTeam.toXml)
      postResponse.!(200, "http response code must be 200")
      val savedTeamXml = postResponse.xml.open_!
      val savedTeam = Team.fromXml(savedTeamXml \\ "response" \\ "team")

      //get all owned teams and test result
      val getAllResponse = get("/webservices/team?ownerId=1")
      getAllResponse.!(200, "http response code must be 200")
      val teams: List[Team] = getAllResponse.xml.open_!
      teams(0).id must beEqual(1)
      teams(1).id must beEqual(2)

      //update team and test result
      savedTeam.name="changed"
      val updatedTeamResponse = put("/webservices/team",savedTeam.toXml)
      updatedTeamResponse.!(200, "http response code must be 200")
      val updatedTeamXml = updatedTeamResponse.xml.open_!
      val updatedTeam = Team.fromXml(updatedTeamXml \\ "response" \\ "team")
      println(updatedTeamXml)
      println(updatedTeam)

    }

    "delete team with id=2" in {
      val response: TestResponse = delete("/webservices/team/2")
      response.!(200, "http response code must be 200")

      val teams = get("/webservices/team=ownerId=1").xml.open_!
      teams.size must beEqual(1)
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
    team.name = "" //insufficient characters (min:1)
    team.description = ""
    team
  }

  def validTeam = {
    val team = new Team
    team.name = "namenamename"
    team.description = "descriptiondescription"
    team
  }
}