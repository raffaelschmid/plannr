package ch.plannr.testing

import org.specs.Specification
import org.specs.specification.BeforeAfter
import ch.plannr.common.persistence.Dataloader
import net.liftweb.http.testing.{ReportFailure, TestKit}

/**
 * Created by IntelliJ IDEA.
 * User: rschmid
 * Date: Jul 26, 2010
 * Time: 10:43:02 PM
 * To change this template use File | Settings | File Templates.
 */

trait IntegrationTestPhase extends Specification with TestKit with BeforeAfter {
  def port = 9999

  def url = "localhost"

  def baseUrl = "http://" + url + ":" + port

  doBeforeSpec({
    System.setProperty("run.mode", "test")
    SingletonServer.startup
  }
    )

  doAfterSpec(SingletonServer.shutdown)

  implicit val reportError = new ReportFailure {
    def fail(msg: String): Nothing = error(msg)
  }
}
//object ServerIntegrationSpecification extends TestKit{
//
//}