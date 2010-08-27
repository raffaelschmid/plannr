package ch.plannr.webservices

import net.liftweb.http.rest.RestHelper
import ch.plannr.model.User
import net.liftweb.http.{S, GetRequest, Req}
import ch.plannr.common.webservice.RESTSupport
import ch.plannr.common.Conversion
import xml.Node
import ch.plannr.services.SearchService

object SearchWebservice extends RestHelper with RESTSupport with Conversion {
  serve {
    // /webservices/search/user?term=raffael
    case r@Req("webservices" :: "search" :: "user" :: _, _, GetRequest) => {
      val term = S.param("term")
      if(term.isDefined){
        try{
          val users = SearchService.fullTextUserSearch(term.open_!)
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