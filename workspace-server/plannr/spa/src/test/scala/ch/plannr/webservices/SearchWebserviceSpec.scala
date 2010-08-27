package ch.plannr.webservices

/**
 * User: Raffael Schmid
 *
 * Sorry for this Specification -> had problems to start/stop jetty server before and after each specification.
 * In this case it catchs the groove.
 */

import _root_.ch.plannr.testing.IntegrationTestPhase
import org.specs.Specification
import net.liftweb.http.testing._
import ch.plannr.model.User
import ch.plannr.common.Conversion
import xml.NodeSeq

class SearchWebserviceSpec extends Specification with IntegrationTestPhase with Conversion{

  "search user with legal term" should {
    "credentials match" in {
      val response:TestResponse = get("/webservices/search/user?term=plannr", buildBasicAuthClient("raffael.schmid@plannr.ch", "plannr"), Nil)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val responseXml = response.xml.open_!
      val users:List[User] = responseXml \\ "response" \\ "users"
      val sortedUsers = users.sortBy(it=>it.id)

      users.size must beEqual(2)
      users(0).firstname must beEqual("Raffael")
      users(1).firstname must beEqual("Flavor")


    
    }
  }
  "search user with illegal term" should {
    "credentials match" in {
      val response:TestResponse = get("/webservices/search/user?term=fa", buildBasicAuthClient("raffael.schmid@plannr.ch", "plannr"), Nil)
      response.!(200, "valid credentials given -> 200 should be returned but wasn't")

      val responseXml = response.xml.open_!
      //println(responseXml)
    }
  }

  implicit def node2ListOfUsers(xml: NodeSeq): List[User] = {
    List((xml \\ "users" \\ "user").map {User.fromXml}: _*).sortWith((a, b) => a.id < b.id)
  }
}