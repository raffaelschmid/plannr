package ch.plannr.services

import ch.plannr.model.User
import net.liftweb.http.S
import ch.plannr.common.mail.{MailTemplates, MailSupport}
import net.liftweb.util.Mailer._
import net.liftweb.common.{Box, Empty}
import net.liftweb.util.{Props, Mailer}
import util.Random

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object UserService extends MailSupport {
  def login = if (User.loggedIn_?) User.currentUser else Empty

  /**
   *  user was created on his own and must validate
   */
  private def sendValidationEmail(user: User): Unit = {
    //send mail to user
    def subject = S.?("sign.up.confirmation")
    val email: String = user.email
    def emailFrom = Props.get("mail.from").open_!
    val validationLink = S.hostAndPath + "/webservices/validate/" + user.id + "?salt=" + user.activationSalt
    val msgXml = MailTemplates.signupMailBody(user.firstname, validationLink)
    def bccEmail: Box[String] = Empty

    sendMail(From(emailFrom), Subject(subject),
      (To(user.email) :: xmlToMailBodyType(msgXml) ::
              (bccEmail.toList.map(BCC(_)))): _*)
  }

  /**
   * user was created by a team owner and must retrieve a confirmation
   */
  private def sendNotificationEmail(user: User): Unit = {
    //send mail to user
    def subject = S.?("registration.foreign.subject")
    val email: String = user.email
    def emailFrom = Props.get("mail.from").open_!
    val notificationLink = S.hostAndPath + "/webservices/registrationform/" + user.id + "?salt=" + user.activationSalt
    val msgXml = MailTemplates.notificationMailBody(user.firstname, notificationLink)
    def bccEmail: Box[String] = Empty

    sendMail(From(emailFrom), Subject(subject),
      (To(user.email) :: xmlToMailBodyType(msgXml) ::
              (bccEmail.toList.map(BCC(_)))): _*)
  }


  def register(user: User, selfRegistration: Boolean): User = {

    //set new activation salt
    user.activationSalt = User.newActivationSalt

    //save user
    user.persist

    if (selfRegistration) {
      sendValidationEmail(user)
    }
    else {
      sendNotificationEmail(user)
    }
    user
  }

  def validate(user: User, salt: Long): Boolean = {
    if (user.activationSalt == salt) {
      user.validated = true
      user.persist
      true
    }
    else {
      false
    }
  }

  def findByEmail(email: String): Box[User] = {
    User.findByEmail(email)
  }

  /**
   * update of data
   */
  def update(newUser: User): User = {
    val savedNewUser = newUser.merge
    savedNewUser
  }
}