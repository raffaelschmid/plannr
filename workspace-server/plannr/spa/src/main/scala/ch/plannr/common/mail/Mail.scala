package ch.plannr.common.mail

import javax.mail.{Authenticator, PasswordAuthentication}
import net.liftweb.common.Full
import net.liftweb.util.{Props, Mailer}
import net.liftweb.util.Mailer.{From, Subject, MailTypes, XHTMLMailBodyType, PlainMailBodyType, MailBodyType}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object Mail {
  def config(host: String, user: String, password: String) {
    System.setProperty("mail.smtp.starttls.enable","true");
    System.setProperty("mail.smtp.auth", "true")
    System.setProperty("mail.mime.charset", "UTF-8")
    Mailer.authenticator = Full(new Authenticator {
      override def getPasswordAuthentication =
        new PasswordAuthentication(user, password)
    })

  }
}

trait MailSupport {
  def sendMail(from: From, subject: Subject, rest: MailTypes*) {

    val infoList = rest.toList
    val bodyTypes = infoList.flatMap {case x: MailBodyType => Some[MailBodyType](x); case _ => None}
    bodyTypes match {
      case PlainMailBodyType(txt) :: Nil =>
        println("- - - - - - - - - -")
        println("from: " + from.address)
        println("subject: " + subject.subject)
        println(txt)
      case _ =>
        bodyTypes.foreach {
          tab =>
            tab match {
              case PlainMailBodyType(txt) => {
                println("- - - - - - - - - -")
                println("from: " + from.address)
                println("subject: " + subject.subject)
                println(txt)
              }
              case XHTMLMailBodyType(html) => {
                println("- - - - - - - - - -")
                println("from: " + from.address)
                println("subject: " + subject.subject)
                println(html.toString)
              }
              case _ =>
            }
        }
    }
    //Mailer.sendMail(from, subject, rest)
  }
}