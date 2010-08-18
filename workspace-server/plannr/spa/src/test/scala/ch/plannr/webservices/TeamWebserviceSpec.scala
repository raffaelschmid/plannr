package ch.plannr.webservices

import xml.NodeSeq
import ch.plannr.model.Team
import net.liftweb.http.testing.{ReportFailure, TestResponse}
import org.specs.Specification
import ch.plannr.testing.IntegrationTestPhase

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class TeamWebserviceSpec extends Specification with IntegrationTestPhase with TeamTestdata {
  "POST to /webservices/team" should {
    "return error xml if instance is not valid" in {

      val response: TestResponse = post("/webservices/team/add?ownerId=1", invalidTeam.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val xml = response.xml.open_!
      val violations: NodeSeq = (xml \\ "response" \\ "violations" \\ "violation")
      (violations(0) \ "@property").text.trim must beIn(List("name", "description"))
      (violations(1) \ "@property").text.trim must beIn(List("name", "description"))
    }
    "return error xml if instance is not valid" in {
      val response: TestResponse = post("/webservices/team/add?ownerId=1", validTeam.toXml)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val getResponse: TestResponse = get("/webservices/team?ownerId=1")
      val xml = (getResponse.xml.open_!)

      val teams = List((xml \\ "teams" \\ "team").map {Team.fromXml}: _*).sort((a, b) => a.id < b.id)
      teams(0).id must beEqual(1)
      teams(1).id must beEqual(2)
    }
    "delete team with id=2" in {
      val response: TestResponse = delete("/webservices/team/2")
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      //TODO test retrieve 2      

    }

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