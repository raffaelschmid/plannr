package ch.plannr.testing

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.Server
import org.mortbay.jetty.webapp.WebAppContext

import net.liftweb.http.testing.{TestRunner, TestFramework, TestKit}
import org.specs.Specification
import org.specs.specification.BeforeAfter
import xml.Node
import net.liftweb.common.{Box, Failure, Full}

/**
 * Created by IntelliJ IDEA.
 * User: rschmid
 * Date: Jul 26, 2010
 * Time: 10:43:02 PM
 * To change this template use File | Settings | File Templates.
 */

trait ServerIntegrationSpecification extends Specification with BeforeAfter with TestKit{
  override def baseUrl = SingletonServer.baseUrl

  implicit def responseType2Node(response:ResponseType): Node = response.xml match {
    case x: Failure => <error>$
      {x.msg}
    </error>
    case x@Full(m) => x.open_!
    case _ => <error>undefined</error>
  }

  doBeforeSpec(SingletonServer.startup)
  doAfterSpec {SingletonServer.shutdown}
}
//object ServerIntegrationSpecification extends TestKit{
//
//}