package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.model.User
import ch.plannr.services.UserService
import net.liftweb.http.{S, GetRequest, Req, PostRequest}
import javax.validation.ConstraintViolationException
import net.liftweb.common.Full
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.common.Conversion

object UserWebservice extends RestHelper with RESTSupport with Conversion{
  serve {
    // /webservices/login
    case r@Req("webservices" :: "login" :: _, _, PostRequest) => {
      val u = UserService.login
      if (u.isDefined)
        xmlSuccess(u.open_!.toXml)
      else xmlError("error while logging you in")
    }
    // /webservices/register
    case r@Req("webservices" :: "register" :: _, _, PostRequest) => {

      val wasSelfRegistered = retrieveSelfRegistrationParam

      try {
        val user = User.fromXml(r.xml.open_!)
        val registeredUser = UserService.register(user, wasSelfRegistered)

        xmlSuccess(registeredUser.toXml)
      }
      catch {
        case ex: ConstraintViolationException =>
          xmlViolation(ex.getConstraintViolations)
        case ex: Exception =>
          xmlError(ex.getMessage)
      }
    }
    // /webservices/validate/3?salt=23323
    case r@Req("webservices" :: "validate" :: userid :: _, _, GetRequest) => {

      try {
        val salt = S.param("salt").open_!.toLong
        val user = User.findById(userid.toLong).open_!
        if (UserService.validate(user, salt))
          xmlSuccess
        else
          xmlError("error while user validation: salt not valid")

      }
      catch {
        case ex: Exception => xmlError("error while user validation: userid not found")
      }
    }
    case r@Req("webservices" :: "update" :: userid :: _, _, PostRequest) => {
      try {
        val user = User.fromXml(r.xml.open_!)
        val updatedUser = UserService.update(user)

        xmlSuccess(updatedUser.toXml)
      }
      catch {
        case ex: ConstraintViolationException =>
          xmlViolation(ex.getConstraintViolations)
        case ex: Exception =>
          xmlError(ex.getMessage)
      }
    }
    case r@Req("webservices" :: "user" :: "find" :: _, _, GetRequest) => {
      if (S.param("email").isDefined) {
        val email = S.param("email").open_!
        val user = UserService.findByEmail(email)
        if (user.isDefined) {
          xmlSuccess(user.open_!.toXml)
        }
        else {
          xmlError("no user found")
        }
      }
      else {
        xmlError("email parameter not found")
      }
    }
  }
  private def retrieveSelfRegistrationParam: Boolean = {
      S.param("self") match {
        case Full(self) => self.toBoolean
        case _ => false
      }
  }
}