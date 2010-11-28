package ch.plannr.templates

import net.liftweb.http.S

object MailTemplate{

  def passwordResetMailBody(firstname:String, resetLink: String) = {
    (<html>
      <head>
        <title>
          {S.??("reset.password.confirmation")}
        </title>
      </head>
      <body>
        <p>
          {S.??("dear")}{firstname},
            <br/>
            <br/>{S.??("click.reset.link")}<br/> <a href={resetLink}>
          {resetLink}
        </a>
            <br/>
            <br/>{S.??("thank.you")}
        </p>
      </body>
    </html>)
  }

  def notificationMailBody(firstname: String, notificationLink: String) = {
     (<html>
       <head>
         <title>
           {S.?("registration.foreign.subject")}
         </title>
       </head>
       <body>
         <p>
           {S.??("dear")} {firstname},
             <br/>
             <br/>{S.?("registration.foreign.message")}<br/> <a href={notificationLink}>
           {notificationLink}
         </a>
             <br/>
             <br/>{S.??("thank.you")}
         </p>
       </body>
     </html>)
   }

   def signupMailBody(firstname: String, validationLink: String) = {
     (<html>
       <head>
         <title>
           {S.??("sign.up.confirmation")}
         </title>
       </head>
       <body>
         <p>
           {S.??("dear")} {firstname},
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