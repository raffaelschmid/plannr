package ch.plannr.model

import net.liftweb.util.Mailer
import ch.plannr.common.persistence.DBModel
import net.liftweb.common.{Loggable, Full, Empty, Box}
import net.liftweb.http.{SessionVar, CleanRequestVarOnSessionTransition, RequestVar, S}

/**
 * User: Raffael Schmid
 *
 * TODO
 */

trait MegaBasicUser[T <: MegaBasicUser[T]] {
  self: T =>

  def id: Long

  def validated: Boolean

  def password: String
}

trait MetaMegaBasicUser[ModelType <: MegaBasicUser[ModelType]] extends Loggable {
  self: ModelType =>


  private object curUser extends RequestVar[Box[ModelType]](Empty)

  def currentUser: Box[ModelType] = curUser.is

  var onLogIn: List[ModelType => Unit] = Nil

  var onLogOut: List[Box[ModelType] => Unit] = Nil

  var autologinFunc: Box[() => Unit] = Empty

  def loggedIn_? = {
    currentUser.isDefined
  }

  def logUserIn(who: ModelType) {
    onLogIn.foreach(_(who))
    curUser(Full(who))
  }

  def logoutCurrentUser = logUserOut()

  def logUserOut() {
    onLogOut.foreach(_(curUser))
    curUser.remove()
    S.request.foreach(_.request.session.terminate)
  }


  def login(usernameOrEmailValue: String, passwordValue: String): ModelType = {


    val user = findByUsername(usernameOrEmailValue)
    user match {
      case Full(user) if user.validated &&
              user.password == (passwordValue) => {
        logger.info("log user in")
        logUserIn(user)
        user
      }
      case Full(user) if !user.validated => {
        logger.info("user not validated")
        throw new SecurityException(S.??("account.validation.error"))
      }

      case _ => {
        logger.info("invalid credentials")
        throw new SecurityException(S.??("invalid.credentials"))
      }
    }
  }

  def findAll: List[User] = {
    val users = DBModel.createNamedQuery[User]("findAllUsers").getResultList()
    List(users: _*)
  }

  def findById(id: Long): Box[ModelType] = {
    val user: ModelType = DBModel.createNamedQuery[ModelType]("findById", Pair("id", id)).getSingleResult
    user match {
      case _ => Full(user)
    }
  }

  def findByUsername(username: String): Box[ModelType] = {
    val user: ModelType = DBModel.createNamedQuery[ModelType]("findByUsername", Pair("username", username)).getSingleResult
    user match {
      case _ => Full(user)
    }
  }

  def findByUsernameOrEmail(usernameOrEmail: String): Box[ModelType] = {
    val user = DBModel.createNamedQuery("findByUsernameOrEmail", Pair("username", usernameOrEmail), Pair("email", usernameOrEmail)).getSingleResult
    user match {
      case _ => Full(user)
    }
  }

}