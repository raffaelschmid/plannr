/*
 * Copyright 2008 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package bootstrap.liftweb

import _root_.net.liftweb.http._
import auth.{userRoles, AuthRole, HttpBasicAuthentication}
import ch.plannr.model._
import ch.plannr.common.persistence.TransactionalLoanWrapper
import ch.plannr.webservices._
import net.liftweb.sitemap.{Loc, Menu, SiteMap}
import net.liftweb.sitemap.Loc._
import net.liftweb.common.{Box, Full}
import javax.mail.{Authenticator, PasswordAuthentication}
import ch.plannr.common.mail.Mail
import net.liftweb.util.{Props, Mailer}
import bootstrap.config.Beans
import ch.plannr.common.di.Context

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */

class Boot {
  def boot {

    Beans.init
    Mail.configure
    LiftRules.resourceNames = "messages" :: LiftRules.resourceNames

    LiftRules.addToPackages("ch.plannr")

    LiftRules.httpAuthProtectedResource.append {
      case Req("webservices" :: "login" :: _, _, _) => Full(AuthRole("admin"))
    }

    val entries = Menu(Loc("Home", List("index"), "Home")) :: Menu(Loc("Manager", List("manager"), "Manager", If(User.loggedIn_? _, S.??("must.be.logged.in")))) :: User.sitemap

    LiftRules.setSiteMap(SiteMap(entries: _*))
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    LiftRules.dispatch.append(Context.inject_![SearchWebservice])
    LiftRules.dispatch.append(Context.inject_![VacationWebservice])
    LiftRules.dispatch.append(Context.inject_![TeamWebservice])


    S.addAround(new TransactionalLoanWrapper())

  }
}

