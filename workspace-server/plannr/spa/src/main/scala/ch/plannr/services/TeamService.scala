package ch.plannr.services

import ch.plannr.common.mail.MailSupport
import ch.plannr.model.Team

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
object TeamService  extends MailSupport{


  /**
   * update of data
   */
  def save(newTeam: Team): Team = {
    val savedNewTeam = newTeam.persist
    savedNewTeam
  }

  def update(teamToUpdate: Team): Team = {
    val updateTeam = teamToUpdate.merge
    updateTeam
  }
}