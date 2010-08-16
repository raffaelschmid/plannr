package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.model.User
import ch.plannr.common.webservice.RESTSupport
import javax.validation.ConstraintViolationException
import collection.JavaConversions
import ch.plannr.services.UserService
import net.liftweb.http.{S, GetRequest, Req, PostRequest}
import net.liftweb.util.Props

object UserWebservice extends RestHelper with RESTSupport {
  serve {
    // /webservices/login
    case "webservices" :: "login" :: _ Post _ => {
      val u = UserService.login
      if (u.isDefined)
        u.open_!.toXml
      else xmlError("error while logging you in")
    }
    // /webservices/register
    case r@Req("webservices" :: "register" :: _, _, PostRequest) => {

      try {
        println("runmode:" + Props.mode)

        val user = User.fromXml(r.xml.open_!)
        val registeredUser = UserService.register(user)

        registeredUser.toXml
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
    // /webservices/validate/3?salt=23323
    case r@Req("webservices" :: "validate" :: userid :: _, _, GetRequest) => {

      try {
        val salt = S.param("salt").open_!.toLong
        if (UserService.validate(userid.toLong, salt))
          xmlSuccess
        else
          xmlError("error while user validation: salt not valid")

      }
      catch {
        case ex: Exception => xmlError("error while user validation: userid not found")
      }
    }
  }

}