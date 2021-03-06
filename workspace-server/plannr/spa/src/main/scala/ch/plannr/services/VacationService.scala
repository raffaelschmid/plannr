package ch.plannr.services

import ch.plannr.model.Vacation

abstract class VacationService{
  def save(vacation: Vacation): Vacation

}
object VacationServiceImpl extends VacationService {
  def save(vacation: Vacation): Vacation = {
    vacation.persist
  }
}