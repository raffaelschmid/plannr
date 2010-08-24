package ch.plannr.common.http

import net.liftweb.http.auth.HttpBasicAuthentication
import net.liftweb.http.{InMemoryResponse, LiftResponse, UnauthorizedResponse, Req}

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
case class CustomHttpBasicAuthentication(realmname: String)(func: PartialFunction[(String, String, Req), Boolean]) extends HttpBasicAuthentication(realmname)(func){
  override def unauthorizedResponse: CustomUnauthorizedResponse = CustomUnauthorizedResponse(realm)
  override def realm = realmname
}

case class CustomUnauthorizedResponse(rlm: String) extends UnauthorizedResponse(rlm) {
  override def toResponse = InMemoryResponse(Array(), List(), Nil, 401)
}