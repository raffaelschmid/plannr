package ch.plannr.services

import ch.plannr.model.User
import net.liftweb.http.S
import ch.plannr.common.mail.{MailTemplates, MailSupport}
import net.liftweb.util.Mailer._
import net.liftweb.common.{Box, Empty}
import net.liftweb.util.{Props, Mailer}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object UserService extends MailSupport {
  def login = if (User.loggedIn_?) User.currentUser else Empty


  def register(user: User): User = {
    user.persist

    def subject = S.??("sign.up.confirmation")
    val email: String = user.email
    def emailFrom = Props.get("mail.from").open_!
    val validationLink = S.hostAndPath + "/webservices/validate/" + user.id + "?salt=" + user.activationSalt
    val msgXml = MailTemplates.signupMailBody(user.firstname, validationLink)
    def bccEmail: Box[String] = Empty

    sendMail(From(emailFrom), Subject(subject),
      (To(user.email) :: xmlToMailBodyType(msgXml) ::
              (bccEmail.toList.map(BCC(_)))): _*)

    user
  }

  def validate(userid: Long, salt: Long): Boolean = {

    println(userid + ", " + salt)
    val user = User.findById(userid).open_!
    if (user.activationSalt == salt) {
      user.validated = true
      user.persist
      true
    }
    else {
      false
    }


  }
}