package ch.plannr.services

import org.specs.Specification
import ch.plannr.model.{User, Team}
import ch.plannr.common.persistence.{DBModel, Dataloader}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
//class TeamServiceSpec extends Specification with TeamTestdata with Dataloader {
//
//  //  "save team" should {
//  //    "return saved team while execution with a valid object" in {
//  //      val savedTeam = TeamService.addTeamToOwner(validTeam, 1)
//  //      (savedTeam.id) mustNot beEqualTo(0)
//  //      (savedTeam.name) must beEqualTo(validTeam.name)
//  //      (savedTeam.description) must beEqualTo(validTeam.description)
//  //
//  //      println("-------------->")
//  //
//  //      println(savedTeam.owner)
//  //    }
//  //  }
//  //  "update team" should {
//  //    "return updated team while execution with a valid object" in {
//  //      val savedTeam = TeamService.addTeamToOwner(validTeam, 1)
//  //      savedTeam.name = "floooo"
//  //
//  //      val updatedTeam = TeamService.update(savedTeam)
//  //      (updatedTeam.name) must beEqualTo("floooo")
//  //    }
//  //  }
//  "remove team" should {
//    "remove itself and the reference from its owner" in {
//      val savedTeam = TeamService.addTeamToOwner(validTeam, 1)
//      //      val numberOfOwnedTeams = User.findById(1).open_!.ownerOf.size
//      val user = DBModel.getReference(classOf[User], 1.toLong)
//      val team = DBModel.getReference(classOf[Team], 1.toLong)
//      println("user")
//      println(user)
//      println("team")
//      println(user.ownerOf)
//      TeamService.deleteTeam(team)
//
//
//      //      TeamService.deleteTeam(savedTeam)
//      //
//      //      val user = User.findById(1).open_!
//      //      user.ownerOf = DBModel.getReference(classOf[_root_.java.util.Set[Team]], user.id)
//      //      val numberOfOwnedTeamsAfterDelete = user.ownerOf.size
//      //      println(numberOfOwnedTeamsAfterDelete)
//      //
//      //      (numberOfOwnedTeams - 1) must beEqual(numberOfOwnedTeamsAfterDelete)
//
//    }
//  }
//}
//trait TeamTestdata {
//  def invalidTeam = {
//    val team = new Team
//    team.name = "name" //insufficient characters (min:5)
//    team.description = "description" //insufficient characters(min:20)
//    team
//  }
//
//  def validTeam = {
//    val team = new Team
//    team.name = "namename"
//    team.description = "descriptiondescription"
//    team
//  }
//}