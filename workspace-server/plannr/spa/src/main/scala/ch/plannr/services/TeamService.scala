package ch.plannr.services

import ch.plannr.common.mail.MailSupport
import ch.plannr.model.{User, Team}
import scala.collection.JavaConversions._
import ch.plannr.common.persistence.DBModel
import net.liftweb.common.Loggable

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object TeamService extends MailSupport with Loggable {


  /**
   * update of data
   */
  def addTeamToOwner(newTeam: Team, ownerId: Long): Team = {
    val userBox = User.findById(ownerId)
    if (userBox.isDefined) {
      val user = userBox.open_!
      newTeam.owner = user

      newTeam.persistAndFlush
    }
    else {
      throw new IllegalArgumentException("userid not found")
    }
  }

  def delete(team: Team) {
    team.members.foreach{
      member:User=>member.memberOf.remove(team)
      member.persist  
    }
    team.remove
  }

  def update(teamToUpdate: Team): Team = {
    val updateTeam = teamToUpdate.merge
    updateTeam
  }

  def addUsersToTeam(teamId: Long, users: List[User]): List[User] = {
    def attachUser(user: User): User = {
      if (user.id != 0) {
        user.merge
      }
      else {
        user.persist
        user
      }
    }

    var team: Team = Team.findById(teamId).get
    for (user <- users) {
      val savedUser = attachUser(user)
      savedUser.memberOf.add(team)
      team.members.add(savedUser)
    }
    team.flush
    List() ++ asSet(team.members)
  }

  /**
   * mappedBy is over user.memberOf. execution is as follow
   * 1. loading user
   * 2. find particular team inside user.memberOf and remove it -> depending on equal and hashCode implementation is the most secure approach
   * 3. flush persistence session
   * 4. return the new list of all members of the specified team
   */
  def deleteUserFromTeam(teamId: Long, memberId: Long): List[User] = {

    val user = User.findById(memberId).open_!
    val team = Team.findById(teamId).open_! //(Set() ++ user.memberOf).find(t => t.id == teamId)
    team.members.remove(user)
    user.memberOf.remove(team)
    user.flush

    List() ++ asSet(team.members)
  }
}