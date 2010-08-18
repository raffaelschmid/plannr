package ch.plannr.services

import org.specs.Specification
import ch.plannr.model.Team

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
class TeamServiceSpec extends Specification with TeamTestdata{
  "save team" should {
    "return saved team while execution with a valid object" in {
      val savedTeam = TeamService.save(validTeam)
      (savedTeam.id) mustNot beEqualTo(0)
      (savedTeam.name) must beEqualTo(validTeam.name)
      (savedTeam.description) must beEqualTo(validTeam.description)
    }
  }
  "update team" should {
    "return updated team while execution with a valid object" in {
      val savedTeam = TeamService.save(validTeam)
      savedTeam.name="floooo"

      val updatedTeam = TeamService.update(savedTeam)
      (updatedTeam.name) must beEqualTo("floooo")
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
    team.name = "namename"
    team.description = "descriptiondescription"
    team
  }
}