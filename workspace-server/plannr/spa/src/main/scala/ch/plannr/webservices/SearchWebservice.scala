package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.model.User
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.common.Conversion
import xml.Node
import ch.plannr.services.{SearchService}
import ch.plannr.common.di.Context
import net.liftweb.http.{LiftRules, S, GetRequest, Req}

abstract class SearchWebservice extends LiftRules.DispatchPF

object SearchWebserviceImpl extends SearchWebservice with RestHelper with RESTSupport with Conversion {


  val searchService = Context.inject_![SearchService]

  serve {
    // /webservices/search/user?term=raffael
    case r@Req("webservices" :: "search" :: "user" :: _, _, GetRequest) => {
      val term = S.param("term")
      if(term.isDefined){
        try{
          val users = searchService.fullTextUserSearch(term.open_!)
          xmlSuccess(list2UsersXml(users: _*))
        }
        catch{
          case ex:IllegalArgumentException => xmlError("term must contain at least 3 characters")
        }
      }
      else{
        xmlError("term not found!!!")
      }
   }
  }

  def list2UsersXml(list: User*): Node = {
    val retVal=
    <users>
      {list.map {it: User => it.toXml}}
    </users>
    retVal
  }

}