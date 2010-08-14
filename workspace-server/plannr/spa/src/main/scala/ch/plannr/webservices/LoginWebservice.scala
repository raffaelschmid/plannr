package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import net.liftweb.json.Extraction
import ch.plannr.model.User
import ch.plannr.common.persistence.DBModel
import ch.plannr.common.webservice.RESTSupport
import net.liftweb.http.{Req, PostRequest}

object LoginWebservice extends RestHelper with RESTSupport{
  
  serve {
    case "webservices" :: "login" :: _ Post _ => {
      if (User.loggedIn_?) User.currentUser.open_!.toXml else xmlError("error while logging you in")
    }
    case r @ Req("webservices" :: "register" :: _, _, PostRequest) => {

      println("-------------------")
      println(r.xml)
      xmlError("unsupported")
    }
  }

}