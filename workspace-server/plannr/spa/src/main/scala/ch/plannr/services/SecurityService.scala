package ch.plannr.services

import ch.plannr.model.User

abstract class SecurityService{
  def isUserLoggedIn:Boolean
  def currentUser:User
}

object SecurityServiceImpl extends SecurityService{
  def isUserLoggedIn:Boolean = User.currentUser.isDefined
  def currentUser:User = User.currentUser.open_!
}
