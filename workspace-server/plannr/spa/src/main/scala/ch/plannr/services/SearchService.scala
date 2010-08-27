package ch.plannr.services

import net.liftweb.common.Loggable
import ch.plannr.model.User

/**
 * User: Raffael Schmid
 * 
 * TODO
 */
object SearchService extends Loggable {
  def fullTextUserSearch(term:String):List[User]={
    User.findFullTextLike(term)  
  }
}