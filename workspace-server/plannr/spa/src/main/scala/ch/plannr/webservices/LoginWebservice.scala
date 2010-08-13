package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import net.liftweb.json.Extraction
import ch.plannr.model.User
import ch.plannr.common.persistence.DBModel

object LoginWebservice extends RestHelper {
  case class UserInfo(firstName: String, lastName: String, email: String) {
    def toXml = <user>
      <firstname>
        {firstName}
      </firstname> <lastname>
        {lastName}
      </lastname> <email>
        {email}
      </email>
    </user>

    def toJson = Extraction.decompose(this)
  }


  // define a REST handler for an XML request
  serve {
    case "webservices" :: "login" :: _ Post _ => {


      UserInfo("Raffael", "Schmid", "raffi.schmid@gmail.com").toXml
    }
  }

  //  implicit def userToInfo(u: User): UserInfo =
  //    UserInfo(u.firstName, u.lastName, u.email)
  //
  //  implicit def uLstToInfo(ul: List[User]): List[UserInfo] =
  //    ul.map(userToInfo)

}