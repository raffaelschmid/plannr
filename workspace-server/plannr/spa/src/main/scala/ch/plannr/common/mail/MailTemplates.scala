package ch.plannr.common.mail

import net.liftweb.http.S

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object MailTemplates {
  def signupMailBody(firstname: String, validationLink: String) = {
    (<html>
      <head>
        <title>
          {S.??("sign.up.confirmation")}
        </title>
      </head>
      <body>
        <p>
          {S.??("dear")}{firstname},
            <br/>
            <br/>{S.??("sign.up.validation.link")}<br/> <a href={validationLink}>
          {validationLink}
        </a>
            <br/>
            <br/>{S.??("thank.you")}
        </p>
      </body>
    </html>)
  }
}