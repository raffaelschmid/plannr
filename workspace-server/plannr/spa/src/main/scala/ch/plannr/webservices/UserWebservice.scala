package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.model.User
import ch.plannr.common.webservice.RESTSupport
import net.liftweb.http.{Req, PostRequest}
import javax.validation.ConstraintViolationException
import collection.JavaConversions

object UserWebservice extends RestHelper with RESTSupport {
  serve {
    case "webservices" :: "login" :: _ Post _ => {
      if (User.loggedIn_?) User.currentUser.open_!.toXml else xmlError("error while logging you in")
    }
    case r@Req("webservices" :: "register" :: _, _, PostRequest) => {

      try {

        val user = User.fromXml(r.xml.open_!)
        println(user)
        user.persist

        user.toXml
      }
      catch {
        case ex: ConstraintViolationException =>
          val set = Set() ++ (JavaConversions.asSet(ex.getConstraintViolations))
          //set.foreach(println(_.getMessage))
          xmlViolation(set)
        case ex: Exception =>
          xmlMessage(ex.getMessage)
      }
    }
  }

}