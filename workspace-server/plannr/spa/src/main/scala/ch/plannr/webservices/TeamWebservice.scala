package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.common.webservice.RESTSupport
import javax.validation.ConstraintViolationException
import collection.JavaConversions
import net.liftweb.http.{S, GetRequest, Req, PostRequest}
import net.liftweb.util.Props
import ch.plannr.model.{Team, User}
import ch.plannr.services.{TeamService, UserService}

object TeamWebservice extends RestHelper with RESTSupport {
  serve {
    // /webservices/team
    case r@Req("webservices" :: "team" :: _, _, PostRequest) => {

      try {
        val team = Team.fromXml(r.xml.open_!)
        val savedTeam = TeamService.save(team)
        
        savedTeam.toXml
      }
      catch {
        case ex: ConstraintViolationException =>
          val set = Set() ++ (JavaConversions.asSet(ex.getConstraintViolations))
          xmlViolation(set)
        case ex: Exception =>
          xmlMessage(ex.getMessage)
      }
    }

  }
}