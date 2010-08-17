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
import xml.{NodeSeq, Node}
import common.FullEquality

/**
 *
 */
@Entity
@Table(name = "TBL_USER")
class User extends MegaBasicUser[User] with Domain with Persistent[User] {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "ACTIVATION_SALT", nullable = false)
  var activationSalt: Long = _

  @Column(name = "EMAIL", unique = true, nullable = false)
  @Email
  var email: String = _

  @Column(name = "PASSWORD", nullable = false)
  @Size(min = 6, max = 10)
  var password: String = _


  @Column(name = "FIRST_NAME", nullable = false)
  @NotEmpty
  var firstname: String = _

  @Column(name = "LAST_NAME", nullable = false)
  @NotEmpty
  var lastname: String = _

  @Column(name = "VALIDATED")
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
      <password>
        {password}
      </password>
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
      <address>
        <street1>
          {address.street1}
        </street1>
        <street2>
          {address.street2}
        </street2>
        <zip>
          {address.zip}
        </zip>
        <city>
          {address.city}
        </city>
        <countryCode>
          {address.countryCode}
        </countryCode>
      </address>
    </user>


  /**
   * persistence provider needs carefully selected properties for equality check
   */
  override def equals(other: Any): Boolean =
    other match {
      case that: User =>
        (that canEqual this) && email == that.email
      case _ => false
    }

  /**
   * persistence provider needs carefully selected properties for calculating hashCode
   * email should not be null in any case but because of it is an entity it's a var
   */
  override def hashCode: Int = 41 + email.hashCode

  def canEqual(other: Any): Boolean = other.isInstanceOf[User]
}
object User extends User with MetaMegaBasicUser[User] with FullEquality {
  def fromXml(xml: Node): User = {


    val user = new User()
    user.id = xml \ "id"
    user.password = xml \ "password"
    user.firstname = xml \ "firstname"
    user.lastname = xml \ "lastname"
    user.email = xml \ "email"
    user.password = xml \ "password"

    val address = new Address
    user.address = address
    user.address.street1 = xml \ "address" \ "street1"
    user.address.street2 = xml \ "address" \ "street2"
    user.address.zip = xml \ "address" \ "zip"
    user.address.city = xml \ "address" \ "city"
    user.address.countryCode = xml \ "address" \ "country_code"

    user
  }

  implicit def extract(s: NodeSeq): String = {
    val value = s.text.trim
    if (value.isEmpty) null else value
  }

  implicit def string2Long(ns: NodeSeq): Long = {
    val s = extract(ns)
    if (s != null) s.toLong else 0
  }

  implicit def string2Int(ns: NodeSeq): Int = {
    val s = extract(ns)
    if (s != null) s.toInt else 0
  }


}
}
}