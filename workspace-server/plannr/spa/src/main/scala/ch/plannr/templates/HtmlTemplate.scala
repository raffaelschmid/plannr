package ch.plannr.templates

import ch.plannr.model.MegaBasicUser
import net.liftweb.http.S

/**
 * User: Raffael Schmid
 *
 * TODO
 */
object HtmlTemplate {
  def editXhtml = {
    (<form method="post" action={S.uri}>
      <p>
        <h2>
          {S.??("edit")}
        </h2>
      </p>
      <p>
        <label for="firstname">Firstname</label> <user:firstname/><lift:Msg id="err_firstname" errorClass="error"/>
      </p>
      <p>
        <label for="lastname">Lastname</label> <user:lastname/><lift:Msg id="err_lastname" errorClass="error"/>
      </p>
      <p>
        <label for="email">Email</label> <user:email/><lift:Msg id="err_email" errorClass="error"/>
      </p>
        <user:submit/>
    </form>)
  }

  def signupXhtml = {
    (<div>
      <h2>
        {S.??("sign.up")}
      </h2>
      <form method="post" action={S.uri}>
        <p>
          <label for="firstname">Firstname</label> <user:firstname/><lift:Msg id="err_firstname" errorClass="error"/>
        </p>
        <p>
          <label for="lastname">Lastname</label> <user:lastname/><lift:Msg id="err_lastname" errorClass="error"/>
        </p>
        <p>
          <label for="email">Email</label> <user:email/><lift:Msg id="err_email" errorClass="error"/>
        </p>
        <p>
          <label for="password">Password</label> <user:password/><lift:Msg id="err_password" errorClass="error"/>
        </p>
        <p>
          <label for="confirm_password">Password Confirmation</label> <user:confirm_password/><lift:Msg id="err_confirm_password" errorClass="error"/>
        </p>
          <user:submit/>
      </form>
    </div>)
  }


  def passwordResetXhtml = {
    (<form method="post" action={S.uri}>
      <p>
        <h2>
          {S.??("reset.your.password")}
        </h2>
      </p>
      <p>
        <label>{S.??("enter.your.new.password")}</label> <user:pwd/><lift:Msg id="err_password" errorClass="error"/>
      </p>
      <p>
        <label>
          {S.??("repeat.your.new.password")}
        </label> <user:pwd/><lift:Msg id="err_confirm_password" errorClass="error"/>
      </p>
      <p>
          <user:submit/>
      </p>
    </form>)
  }

  def changePasswordXhtml = {
    (<form method="post" action={S.uri}>
      <p>
        <h2>
          {S.??("change.password")}
        </h2>
      </p>
      <p>
        <label>
          {S.??("old.password")}
        </label> <user:old_pwd/><lift:Msg id="err_old_password" errorClass="error"/>
      </p>
      <p>
        <label>
          {S.??("new.password")}
        </label> <user:new_pwd/><lift:Msg id="err_password" errorClass="error"/>
      </p>
      <p>
        <label>
          {S.??("repeat.password")}
        </label> <user:new_pwd/><lift:Msg id="err_confirm_password" errorClass="error"/>
      </p>
      <p>
          <user:submit/>
      </p>
    </form>)
  }


  def lostPasswordXhtml = {
    (<form method="post" action={S.uri}>
      <p>
        <h2>
          {S.??("enter.email")}
        </h2>
      </p>
      <p>
        <label>
          {S.??("email.address")}
        </label> <user:email/><lift:Msg id="err_email" errorClass="error"/>
      </p>
      <p>
          <user:submit/>
      </p>
    </form>)
  }

  def loginXhtml(lostPasswordPath:String) = {
    (<form method="post" action={S.uri}>
      <p>
        <h2>
          {S.??("log.in")}
        </h2>
      </p>

      <p>
        <label>
          {S.??("email.address")}
        </label> <user:email/><lift:Msg id="err_email" errorClass="error"/>
      </p>
      <p>
        <label>
          {S.??("password")}
        </label> <user:password/><lift:Msg id="err_password" errorClass="error"/>
      </p>
      <p>
        <a href={lostPasswordPath}>
          {S.??("recover.password")}
        </a>
      </p>
      <p>
          <user:submit/>
      </p>
    </form>)
  }



}