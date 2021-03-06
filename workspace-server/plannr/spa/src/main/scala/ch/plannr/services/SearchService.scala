package ch.plannr.services

import net.liftweb.common.Loggable
import ch.plannr.model.User

abstract class SearchService{
  def fullTextUserSearch(term:String):List[User]
}

object SearchServiceImpl extends SearchService with Loggable {
  def fullTextUserSearch(term:String):List[User]={
    User.findFullTextLike(term)  
  }
}