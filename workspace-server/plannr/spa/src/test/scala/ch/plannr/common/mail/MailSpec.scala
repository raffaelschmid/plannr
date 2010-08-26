package ch.plannr.common.mail

import org.specs.Specification
import net.liftweb.util.Mailer.{From, Subject, To, PlainMailBodyType, XHTMLMailBodyType}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class MailSpec extends Specification with MailSupport {
  //doBeforeSpec(Mail.configure)
  "MailSender" should {
    "should send plain mail" in {

    sendMail(From("plannr.test@gmail.com"),
        Subject("testmail"),
        To("raffi.schmid@gmail.com"),
        PlainMailBodyType("test body"), XHTMLMailBodyType(<html>
          <body>test</body>
        </html>))

      1 must beEqual(1)
    }
  }
}