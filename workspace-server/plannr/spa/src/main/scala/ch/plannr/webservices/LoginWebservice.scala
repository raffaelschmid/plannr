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


  serve {
    case "webservices" :: "login" :: _ Post _ => {

      if (User.loggedIn_?) User.currentUser.open_!.toXml else <error/>
    }
  }

}