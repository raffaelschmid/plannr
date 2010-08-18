package ch.plannr.services

import ch.plannr.common.mail.MailSupport
import ch.plannr.model.{User, Team}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object TeamService extends MailSupport {


  /**
   * update of data
   */
  def addTeamToOwner(newTeam: Team, ownerId: Long): Team = {
    val userBox = User.findById(ownerId)
    if (userBox.isDefined) {
      val user = userBox.open_!
      newTeam.owner=user

      newTeam.persistAndFlush
    }
    else {
      throw new IllegalArgumentException("userid not found")
    }
  }

  def delete(teamToDelete:Team){
    teamToDelete.remove
  }

  def update(teamToUpdate: Team): Team = {
    val updateTeam = teamToUpdate.merge
    updateTeam
  }
}