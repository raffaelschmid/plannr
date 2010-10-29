package ch.plannr.model

import ch.plannr.common.persistence.DBModel
import net.liftweb.common.{Loggable, Full, Empty, Box}
import net.liftweb.mapper.By
import net.liftweb.http._
import js._
import JsCmds._
import S._
import net.liftweb.sitemap.{Loc, Menu}
import _root_.net.liftweb.sitemap.Loc._
import xml.transform.{RewriteRule, RuleTransformer}
import net.liftweb.util.{FieldError, Mailer}
import _root_.net.liftweb.util.Mailer._
import xml.{Elem, NodeSeq, Node}
import _root_.net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.{Template, LocParam, If}
import javax.validation.{Validation, ConstraintViolationException}
import ch.plannr.templates.{MailTemplate, HtmlTemplate}

/**
 * User: Raffael Schmid
 *
 * TODO
 */

trait MegaBasicUser[T <: MegaBasicUser[T]] {
  self: T =>

  def persist(): T

  def merge(): T

  var id: Long

  var activationSalt: Long

  var password: String

  var email: String

  var validated: Boolean

  var selfRegistered: Boolean

  var firstname: String

  var lastname: String
}

trait MetaMegaBasicUser[ModelType <: MegaBasicUser[ModelType]] extends Loggable {
  self: ModelType =>

  def screenWrap: Box[Node] = Empty

  val basePath: List[String] = "user_mgt" :: Nil

  def signUpSuffix = "sign_up"

  lazy val signUpPath = thePath(signUpSuffix)

  def loginSuffix = "login"

  lazy val loginPath = thePath(loginSuffix)

  def lostPasswordSuffix = "lost_password"

  lazy val lostPasswordPath = thePath(lostPasswordSuffix)

  def passwordResetSuffix = "reset_password"

  lazy val passwordResetPath = thePath(passwordResetSuffix)

  def changePasswordSuffix = "change_password"

  lazy val changePasswordPath = thePath(changePasswordSuffix)

  def logoutSuffix = "logout"

  lazy val logoutPath = thePath(logoutSuffix)

  def editSuffix = "edit"

  lazy val editPath = thePath(editSuffix)

  def validateUserSuffix = "validate_user"

  lazy val validateUserPath = thePath(validateUserSuffix)

  def homePage = "/"

  object loginRedirect extends SessionVar[Box[String]](Empty)



  case class MenuItem(name: String, path: List[String],
                      loggedIn: Boolean) {
    lazy val endOfPath = path.last
    lazy val pathStr: String = path.mkString("/", "/", "")
    lazy val display = name match {
      case null | "" => false
      case _ => true
    }
  }

  def thePath(end: String): List[String] = basePath ::: List(end)

  /**
   * Return the URL of the "login" page
   */
  def loginPageURL = loginPath.mkString("/", "/", "")

  def notLoggedIn_? = !loggedIn_?

  lazy val testLogginIn = If(loggedIn_? _, S.??("must.be.logged.in"));

  def loginFirst = If(
    loggedIn_? _,
    () => {
      import net.liftweb.http.{RedirectWithState, RedirectState}
      val uri = S.uriAndQueryString
      RedirectWithState(
        loginPageURL,
        RedirectState(() => {loginRedirect.set(uri)})
        )
    }
    )

  /**
   * The menu item for login (make this "Empty" to disable)
   */
  def loginMenuLoc: Box[Menu] =
    Full(Menu(Loc("Login", loginPath, S.??("login"), loginMenuLocParams)))

  /**
   * The LocParams for the menu item for login.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def loginMenuLocParams: List[LocParam[Unit]] =
    If(notLoggedIn_? _, S.??("already.logged.in")) ::
            Template(() => wrapIt(login)) ::
            Nil

  /**
   * The menu item for logout (make this "Empty" to disable)
   */
  def logoutMenuLoc: Box[Menu] =
    Full(Menu(Loc("Logout", logoutPath, S.??("logout"), logoutMenuLocParams)))

  /**
   * The LocParams for the menu item for logout.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def logoutMenuLocParams: List[LocParam[Unit]] =
    Template(() => wrapIt(logout)) ::
            testLogginIn ::
            Nil

  /**
   * The menu item for creating the user/sign up (make this "Empty" to disable)
   */
  def createUserMenuLoc: Box[Menu] =
    Full(Menu(Loc("CreateUser", signUpPath, S.??("sign.up"), createUserMenuLocParams)))

  /**
   * The LocParams for the menu item for creating the user/sign up.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def createUserMenuLocParams: List[LocParam[Unit]] =
    Template(() => wrapIt(signupFunc.map(_()) openOr signup)) ::
            If(notLoggedIn_? _, S.??("logout.first")) ::
            Nil

  /**
   * The menu item for lost password (make this "Empty" to disable)
   */
  def lostPasswordMenuLoc: Box[Menu] =
    Full(Menu(Loc("LostPassword", lostPasswordPath, S.??("lost.password"), lostPasswordMenuLocParams))) // not logged in

  /**
   * The LocParams for the menu item for lost password.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def lostPasswordMenuLocParams: List[LocParam[Unit]] =
    Template(() => wrapIt(lostPassword)) ::
            If(notLoggedIn_? _, S.??("logout.first")) ::
            Nil

  /**
   * The menu item for resetting the password (make this "Empty" to disable)
   */
  def resetPasswordMenuLoc: Box[Menu] =
    Full(Menu(Loc("ResetPassword", (passwordResetPath, true), S.??("reset.password"), resetPasswordMenuLocParams))) //not Logged in

  /**
   * The LocParams for the menu item for resetting the password.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def resetPasswordMenuLocParams: List[LocParam[Unit]] =
    Hidden ::
            Template(() => wrapIt(passwordReset(snarfLastItem))) ::
            If(notLoggedIn_? _, S.??("logout.first")) ::
            Nil

  /**
   * The menu item for editing the user (make this "Empty" to disable)
   */
  def editUserMenuLoc: Box[Menu] =
    Full(Menu(Loc("EditUser", editPath, S.??("edit.user"), editUserMenuLocParams)))

  /**
   * The LocParams for the menu item for editing the user.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def editUserMenuLocParams: List[LocParam[Unit]] =
    Template(() => wrapIt(editFunc.map(_()) openOr edit)) ::
            testLogginIn ::
            Nil

  /**
   * The menu item for changing password (make this "Empty" to disable)
   */
  def changePasswordMenuLoc: Box[Menu] =
    Full(Menu(Loc("ChangePassword", changePasswordPath, S.??("change.password"), changePasswordMenuLocParams)))

  /**
   * The LocParams for the menu item for changing password.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def changePasswordMenuLocParams: List[LocParam[Unit]] =
    Template(() => wrapIt(changePassword)) ::
            testLogginIn ::
            Nil

  /**
   * The menu item for validating a user (make this "Empty" to disable)
   */
  def validateUserMenuLoc: Box[Menu] =
    Full(Menu(Loc("ValidateUser", (validateUserPath, true), S.??("validate.user"), validateUserMenuLocParams)))

  /**
   * The LocParams for the menu item for validating a user.
   * Overwrite in order to add custom LocParams. Attention: Not calling super will change the default behavior!
   */
  protected def validateUserMenuLocParams: List[LocParam[Unit]] =
    Hidden ::
            Template(() => wrapIt(validateUser(snarfLastItem))) ::
            If(notLoggedIn_? _, S.??("logout.first")) ::
            Nil

  /**
   * An alias for the sitemap property
   */
  def menus: List[Menu] = sitemap // issue 182

  lazy val sitemap: List[Menu] =
  List(loginMenuLoc, logoutMenuLoc, createUserMenuLoc,
    lostPasswordMenuLoc, resetPasswordMenuLoc,
    editUserMenuLoc, changePasswordMenuLoc,
    validateUserMenuLoc).flatten(a => a)


  def skipEmailValidation = false

  def userMenu: List[Node] = {
    val li = loggedIn_?
    ItemList.
            filter(i => i.display && i.loggedIn == li).
            map(i => (<a href={i.pathStr}>
      {i.name}
    </a>))
  }

  protected def snarfLastItem: String =
    (for (r <- S.request) yield r.path.wholePath.last) openOr ""

  lazy val ItemList: List[MenuItem] =
  List(MenuItem(S.??("sign.up"), signUpPath, false),
    MenuItem(S.??("log.in"), loginPath, false),
    MenuItem(S.??("lost.password"), lostPasswordPath, false),
    MenuItem("", passwordResetPath, false),
    MenuItem(S.??("change.password"), changePasswordPath, true),
    MenuItem(S.??("log.out"), logoutPath, true),
    MenuItem(S.??("edit.profile"), editPath, true),
    MenuItem("", validateUserPath, false))

  // def requestLoans: List[LoanWrapper] = Nil // List(curUser)

  var onLogIn: List[ModelType => Unit] = Nil

  var onLogOut: List[Box[ModelType] => Unit] = Nil

  /**
   * This function is given a chance to log in a user
   * programmatically when needed
   */
  var autologinFunc: Box[() => Unit] = Empty

  //def loggedIn_? : Boolean = currentUserEmail.isDefined
  def loggedIn_? = {
    if (!currentUserEmail.isDefined)
      for (f <- autologinFunc) f()
    currentUserEmail.isDefined
  }

  def logUserIdIn(id: String) {
    curUser.remove()
    curUserEmail(Full(id))
  }

  def logUserIn(who: ModelType) {
    curUser.remove()
    curUserEmail(Full(who.email))
    onLogIn.foreach(_(who))
  }

  def logoutCurrentUser = logUserOut()

  def logUserOut() {
    onLogOut.foreach(_(curUser))
    curUserEmail.remove()
    curUser.remove()
    S.request.foreach(_.request.session.terminate)
  }

  private object curUserEmail extends SessionVar[Box[String]](Empty)

  def currentUserEmail: Box[String] = curUserEmail.is

  private object curUser extends RequestVar[Box[ModelType]](currentUserEmail.flatMap(email => findByEmail(email))) with CleanRequestVarOnSessionTransition


  def currentUser: Box[ModelType] = curUser.is


  def signupMailSubject = S.??("sign.up.confirmation")

  def sendValidationEmail(user: ModelType) {
    val resetLink = S.hostAndPath + "/" + validateUserPath.mkString("/") +
            "/" + user.id

    val email: String = user.email

    val msgXml = MailTemplate.signupMailBody(user.firstname, resetLink)

    Mailer.sendMail(From(emailFrom), Subject(signupMailSubject),
      (To(user.email) :: xmlToMailBodyType(msgXml) ::
              (bccEmail.toList.map(BCC(_)))): _*)
  }

  protected object signupFunc extends RequestVar[Box[() => NodeSeq]](Empty)

  def signup = {
    var confirmPassword = ""
    val theUser = create
    theUser.validated = true
    theUser.selfRegistered = true
    val theName = signUpPath.mkString("")

    def testSignup() {
      try {
        theUser.persist
        S.redirectTo(homePage)
      } catch {
        case cve: ConstraintViolationException => {
          println(cve.getConstraintViolations)
          S.redirectTo(homePage)
        }
        case e: Exception => {
          println("unknown error: " + e)
        }
      }
    }

    def innerSignup = bind("user",
      HtmlTemplate.signupXhtml,
      "firstname" -> SHtml.text("", s => theUser.firstname = s, "id" -> "firstname"),
      "lastname" -> SHtml.text("", s => theUser.lastname = s, "id" -> "lastname"),
      "email" -> SHtml.text("", s => theUser.email = s, "id" -> "email"),
      "password" -> SHtml.text("", s => theUser.password = s, "id" -> "password"),
      "password" -> SHtml.text("", s => confirmPassword = s, "id" -> "confirm_password"),
      "submit" -> SHtml.submit(S.??("sign.up"), testSignup _))

    innerSignup
  }

//  def emailFrom = "noreply@" + S.hostName
  def emailFrom = "plannr.test@gmail.com"

  def bccEmail: Box[String] = Empty

  def testLoggedIn(page: String): Boolean =
    ItemList.filter(_.endOfPath == page) match {
      case x :: xs if x.loggedIn == loggedIn_? => true
      case _ => false
    }


  def validateUser(email: String): NodeSeq = findByEmail(email) match {
    case Full(user) if !user.validated =>
      user.validated = true
      //TODO check for save
      S.notice(S.??("account.validated"))
      logUserIn(user)
      S.redirectTo(homePage)

    case _ => S.error(S.??("invalid.validation.link")); S.redirectTo(homePage)
  }

  def login = {
    if (S.post_?) {
      println(S.param("email"))
      S.param("email").
              flatMap(email => findByEmail(email)) match {
        case Full(user) if user.validated &&
                user.password == S.param("password").openOr("*") =>
          S.notice(S.??("logged.in"))
          logUserIn(user)
          //S.redirectTo(homePage)
          val redir = loginRedirect.is match {
            case Full(url) =>
              loginRedirect(Empty)
              url
            case _ =>
              homePage
          }
          S.redirectTo(redir)

        case Full(user) if !user.validated =>
          S.error(S.??("account.validation.error"))

        case _ => S.error(S.??("invalid.credentials"))
      }
    }

    bind("user", HtmlTemplate.loginXhtml(lostPasswordPath.mkString("/", "/", "")),
      "email" -> (FocusOnLoad(<input type="text" name="email"/>)),
      "password" -> (<input type="password" name="password"/>),
      "submit" -> (<input type="submit" value={S.??("log.in")}/>))
  }

  def passwordResetEmailSubject = S.??("reset.password.request")

  def sendPasswordReset(email: String) {
    findByEmail(email) match {
      case Full(user) if user.validated =>
        val resetLink = S.hostAndPath +
                passwordResetPath.mkString("/", "/", "/") + user.id

        val email: String = user.email

        val msgXml = MailTemplate.passwordResetMailBody(user.firstname, resetLink)
        Mailer.sendMail(From(emailFrom), Subject(passwordResetEmailSubject),
          (To(user.email) :: xmlToMailBodyType(msgXml) ::
                  (bccEmail.toList.map(BCC(_)))): _*)

        S.notice(S.??("password.reset.email.sent"))
        S.redirectTo(homePage)

      case Full(user) =>
        sendValidationEmail(user)
        S.notice(S.??("account.validation.resent"))
        S.redirectTo(homePage)

      case _ => S.error(S.??("email.address.not.found"))
    }
  }

  def lostPassword = {
    bind("user", HtmlTemplate.lostPasswordXhtml,
      "email" -> SHtml.text("", sendPasswordReset _),
      "submit" -> <input type="submit" value={S.??("send.it")}/>)
  }

  def passwordReset(id: String) =
    findById(id.toLong) match {
      case Full(user) =>
        def finishSet() {
          //TODO validate
           user.merge
        }

        bind("user", HtmlTemplate.passwordResetXhtml,
          "pwd" -> SHtml.password_*("", (p: List[String]) =>
            user.password = p.head),
          "submit" -> SHtml.submit(S.??("set.password"), finishSet _))
      case _ => S.error(S.??("password.link.invalid")); S.redirectTo(homePage)
    }

  def changePassword = {
    val user = currentUser.open_! // we can do this because the logged in test has happened
    var oldPassword = ""
    var newPassword: List[String] = Nil

    def testAndSet() {
      if (!(user.password==oldPassword) )
        S.error(S.??("wrong.old.password"))
      else {
        if (newPassword(0) == newPassword(1)){
          user.password = newPassword(0)
        //        user.validate match {
        //          case Nil => saveit
        //          case xs => S.error(xs)
        //        }
          user.merge;
          S.notice(S.??("password.changed"));
        }
        else
          S.??("passwords.do.not.match")

        S.redirectTo(homePage)
      }
    }

    bind("user", HtmlTemplate.changePasswordXhtml,
      "old_pwd" -> SHtml.password("", s => oldPassword = s),
      "new_pwd" -> SHtml.password_*("", LFuncHolder(s => newPassword = s)),
      "submit" -> SHtml.submit(S.??("change"), testAndSet _))
  }

  object editFunc extends RequestVar[Box[() => NodeSeq]](Empty)

  def edit = {
    val theUser: ModelType = currentUser.open_!
    val theName = editPath.mkString("")


    val validatorFactory = Validation.buildDefaultValidatorFactory();
    val validator = validatorFactory.getValidator();

    def jpaIsValid[T](obj: T): Boolean = {
      val violations = validator.validate(obj)
      return if (violations == null || violations.size() == 0)
        true
      else false
    }
    def testEdit() {
      try {

        if (jpaIsValid(theUser))
          theUser.merge()
        else
          println("no valid")

        S.notice(S.??("profile.updated"))
        S.redirectTo(homePage)
      } catch {
        case cve: ConstraintViolationException => {
          println(cve.getConstraintViolations)
          cve.printStackTrace()
          S.redirectTo(homePage)

        }
      }
    }

    def innerEdit = bind("user", HtmlTemplate.editXhtml,
      "firstname" -> SHtml.text(theUser.firstname, s => theUser.firstname = s, "id" -> "firstname"),
      "lastname" -> SHtml.text(theUser.lastname, s => theUser.lastname = s, "id" -> "lastname"),
      "email" -> SHtml.text(theUser.email, s => theUser.email = s, "id" -> "email"),
      "submit" -> SHtml.submit(S.??("save"), testEdit _))

    innerEdit
  }

  def logout = {
    logoutCurrentUser
    S.redirectTo(homePage)
  }

  protected def wrapIt(in: NodeSeq): NodeSeq =
    screenWrap.map(new RuleTransformer(new RewriteRule {
      override def transform(n: Node) = n match {
        case e: Elem if "bind" == e.label && "lift" == e.prefix => in
        case _ => n
      }
    })) openOr in

  /**FINDERS*/
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

  def findByEmail(email: String): Box[ModelType] = {
    logger.debug("email: " + email)
    val user: ModelType = DBModel.createNamedQuery[ModelType]("findByEmail", Pair("email", email)).getSingleResult
    user match {
      case _ => Full(user)
    }
  }

  def findByUserIdAndSalt(userId: Long, salt: Long): Box[ModelType] = {
    val user = DBModel.createNamedQuery("findByUserIdAndSalt", Pair("userId", userId), Pair("salt", salt)).getSingleResult
    user match {
      case _ => Full(user)
    }
  }

  def findFullTextLike(term: String): List[User] = {
    if (term.size < 3)
      throw new IllegalArgumentException("term must contain at least 3 characters")

    val users = DBModel.createNamedQuery[User]("fullTextUserSearch", Pair("keyword", "%" + term + "%")).getResultList
    List(users: _*)
  }

  def create: ModelType

}