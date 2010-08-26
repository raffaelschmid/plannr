//package ch.plannr.common.http
//
//import net.liftweb.common.{Empty, Full, Box}
//import org.apache.commons.codec.binary.Base64
//import net.liftweb.http.{UnauthorizedResponse, InMemoryResponse, LiftResponse, Req}
//import net.liftweb.http.auth.{userRoles, AuthRole, HttpBasicAuthentication, HttpAuthentication}
//
///**
// * User: Raffael Schmid
// *
// * TODO
// */
//case class CustomHttpBasicAuthentication(realmName: String)(func: PartialFunction[(String, String, Req), Boolean]) extends HttpAuthentication {
//  override def unauthorizedResponse: CustomUnauthorizedResponse = CustomUnauthorizedResponse()
//
//  def credentials(r: Req): Box[(String, String)] = {
//    header(r).flatMap(auth => {
//      val decoded = new String(Base64.decodeBase64(auth.substring(6, auth.length).getBytes)).split(":").toList
//      decoded match {
//        case userName :: password :: _ => Full((userName, password))
//        case userName :: Nil => Full((userName, ""))
//        case _ => Empty
//      }
//    }
//      )
//  }
//
//  override def realm = realmName
//
//  def verified_? = {
//    case (req) => {
//      credentials(req) match {
//        case Full((user, pwd)) if (func.isDefinedAt(user, pwd, req)) =>
//          func(user, pwd, req)
//        case _ => false
//      }
//    }
//  }
//
//}
//
//case class CustomUnauthorizedResponse() extends UnauthorizedResponse("") {
// override def toResponse = {
//   InMemoryResponse(Array(), List("a" -> ("a")), Nil, 401)
// }
//}