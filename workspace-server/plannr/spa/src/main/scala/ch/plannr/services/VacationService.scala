package ch.plannr.services

import ch.plannr.model.Vacation

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object VacationService {
  def save(vacation: Vacation): Vacation = {
    vacation.persist
    //TODO send email 

    vacation
  }
}