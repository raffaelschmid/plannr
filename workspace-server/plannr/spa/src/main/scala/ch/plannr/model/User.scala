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
package ch.plannr {
package model {

import _root_.javax.persistence._
import javax.validation.constraints.Size
import org.hibernate.validator.constraints.{NotEmpty, Email}
import common.persistence.{Domain, Persistent, DBModel}

/**
 *
 */
@Entity
@Table(name = "TBL_USER")
class User extends MegaBasicUser[User] with Domain with Persistent {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(unique = true, nullable = false)
  var username: String = ""

  @Column(nullable = false)
  @Size(min = 6, max = 10)
  var password: String = ""

  @Column(unique = true, nullable = false)
  @Email
  var email: String = ""

  @Column(nullable = false)
  @NotEmpty
  var firstname: String = ""

  @Column(nullable = false)
  @NotEmpty
  var lastname: String = ""

  @Column
  var validated: Boolean = false

  @Embedded
  var address: Address = new Address


  @ManyToMany
  @JoinTable(name = "USER_ROLE",
    joinColumns = Array(new JoinColumn(name = "USER_ID", referencedColumnName = "ID")),
    inverseJoinColumns = Array(new JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")))
  var roles: _root_.java.util.Set[Role] = new _root_.java.util.HashSet[Role]()

  override def toXml =
    <user>
      <id>
        {id}
      </id>
      <username>
        {username}
      </username>
      <firstname>
        {firstname}
      </firstname>
      <lastname>
        {lastname}
      </lastname>
      <email>
        {email}
      </email>
      <validated>
        {validated}
      </validated>
    </user>


}
object User extends User with MetaMegaBasicUser[User] {
}
}
}