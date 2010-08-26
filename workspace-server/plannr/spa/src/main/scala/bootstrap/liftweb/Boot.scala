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

import _root_.net.liftweb.common.Full
import _root_.net.liftweb.http._
import auth.{userRoles, AuthRole, HttpBasicAuthentication}
import ch.plannr.model._
import ch.plannr.common.persistence.TransactionalLoanWrapper
import ch.plannr.webservices._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    LiftRules.resourceNames = "messages" :: LiftRules.resourceNames

    LiftRules.addToPackages("ch.plannr")

    LiftRules.httpAuthProtectedResource.append {
      case Req("webservices" :: "login" :: _, _, _) => Full(AuthRole("admin"))
    }


    LiftRules.authentication = HttpBasicAuthentication("plannr")
              {
      case (email, password, req) => {
        try {
          val user = User.login(email, password)
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
    LiftRules.dispatch.append(TeamWebservice)
    LiftRules.dispatch.append(VacationWebservice)

    S.addAround(new TransactionalLoanWrapper())

  }
}

