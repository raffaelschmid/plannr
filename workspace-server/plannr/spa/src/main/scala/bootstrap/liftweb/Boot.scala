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

import _root_.java.util.Locale

import _root_.net.liftweb.common.{Box, Empty, Full}
import _root_.net.liftweb.util.{LoanWrapper, LogBoot}
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import auth.{userRoles, AuthRole, HttpBasicAuthentication}
import ch.plannr.model._
import S.?
import ch.plannr.webservices.UserWebservice
import ch.plannr.common.persistence.{TransactionalLoanWrapper, DBModel}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    LiftRules.addToPackages("ch.plannr")

    LiftRules.httpAuthProtectedResource.append {
      case Req("webservices" :: "login" :: _, _, _) => Full(AuthRole("admin"))
    }


    LiftRules.authentication = HttpBasicAuthentication("lift") {
      case (usernameOrEmail, password, req) => {
        try {
          val user = User.login(usernameOrEmail, password)
          userRoles(AuthRole("admin"))
          true
        }
        catch {
          case se: SecurityException => {
            false
          }
          case _ => false
        }
      }
    }
    LiftRules.loggedInTest = Full(() => true)

    LiftRules.dispatch.append(UserWebservice)

    S.addAround(new TransactionalLoanWrapper())

  }
}

