package ch.plannr.webservices

import net.liftweb.http.testing.TestResponse
import ch.plannr.testing.IntegrationTestPhase
import ch.plannr.common.Conversion
import org.specs.Specification
import java.util.Calendar
import ch.plannr.model.{Team, Vacation}


/**
 * User: Raffael Schmid
 *
 * TODO
 */
class VacationWebserviceSpec extends Specification with IntegrationTestPhase with VacationTestdata with Conversion {
  "POST to /webservices/vacation" should {
    "add vacation to specified group id" in {

      val response: TestResponse = post("/webservices/vacation/add?userId=1&teamId=1", vacation01.toXml)
      response.!(200, "http response code must be 200")

      val vacation = Vacation.fromXml(response.xml.open_!)
      vacation.id must beEqual(1)
      vacation.title must beEqual(vacation01.title)
      vacation.description must beEqual(vacation01.description)
      vacation.from must beEqual(vacation01.from)
      vacation.to must beEqual(vacation01.to)
    }
  }
}
trait VacationTestdata {
  def resetTime(cal: Calendar): Calendar = {
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.MILLISECOND, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal
  }

  def from = resetTime(Calendar.getInstance).getTime

  def to = {
    val cal = Calendar.getInstance
    cal.add(Calendar.DAY_OF_YEAR, 5)
    resetTime(cal).getTime
  }


  def vacation01 = {
    val v = new Vacation
    v.title = "Vacation California 2010"
    v.description = "3 weeks with family"
    v.from = from
    v.to = to
    v
  }

}