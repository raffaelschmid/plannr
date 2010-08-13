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
import common.persistence.{Persistent, DBModel}

/**
 *
 */
@Entity
@Table(name = "tbl_user")
class User extends ToString with Persistent {
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

  @Embedded
  var address: Address = new Address


  @ManyToMany
  @JoinTable(name = "USER_ROLE",
    joinColumns = Array(new JoinColumn(name = "USER_ID", referencedColumnName = "ID")),
    inverseJoinColumns = Array(new JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")))
  var roles: _root_.java.util.Set[Role] = new _root_.java.util.HashSet[Role]()

  

}
object User extends User {
  
  def findAll: List[User] = {
    val users = DBModel.createNamedQuery[User]("findAllUsers").getResultList()
    List(users: _*)
  }
  def findByUsername(username:String):User = {
    DBModel.createNamedQuery[User]("findByUsername",Pair("username","schmidic")).getSingleResult
  }

}
}
}