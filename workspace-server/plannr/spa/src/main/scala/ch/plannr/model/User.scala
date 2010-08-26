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
import org.hibernate.validator.constraints.{NotEmpty, Email}
import xml.NodeSeq
import javax.validation.constraints.{NotNull, Size}
import util.Random
import common.{Conversion, FullEquality}
import common.persistence.{MetaDomain, Domain, Persistent}
import collection.JavaConversions._


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
  @NotNull
  var activationSalt: Long = _


  @Column(name = "EMAIL", unique = true, nullable = false)
  @Email
  @NotNull
  var email: String = _

  @Column(name = "PASSWORD")
  @Size(min = 6, max = 10)
  var password: String = _


  @Column(name = "FIRST_NAME", nullable = false)
  @NotNull
  @NotEmpty
  var firstname: String = _

  @Column(name = "LAST_NAME", nullable = false)
  @NotNull
  @NotEmpty
  var lastname: String = _

  @Column(name = "VALIDATED")
  @NotNull
  var validated: Boolean = false

  @Column(name = "SELF_REGISTERED")
  @NotNull
  var selfRegistered: Boolean = false

  @Embedded
  var address: Address = new Address

  @ManyToMany
  @JoinTable(name = "ROLEMAPPING",
    joinColumns = Array(new JoinColumn(name = "USER_ID", referencedColumnName = "ID")),
    inverseJoinColumns = Array(new JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")))
  var roles: _root_.java.util.Set[Role] = new _root_.java.util.HashSet[Role]()

  @ManyToMany
  @JoinTable(
    name = "MEMBERSHIP",
    joinColumns = Array(new JoinColumn(name = "USER_ID", referencedColumnName = "ID")),
    inverseJoinColumns = Array(new JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")))
  var memberOf: _root_.java.util.Set[Team] = new _root_.java.util.HashSet[Team]()


  @OneToMany(mappedBy = "owner")
  var ownerOf: _root_.java.util.Set[Team] = new _root_.java.util.HashSet[Team]

  @OneToMany(mappedBy = "user")
  var vacations: _root_.java.util.Set[Vacation] = new _root_.java.util.HashSet[Vacation]

  @OneToMany(mappedBy = "user")
  var comments: _root_.java.util.Set[Comment] = _


  override def toXml = {
    <user>
      <id>
        {id}
      </id>
      <activationSalt>
        {activationSalt}
      </activationSalt>
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
      <selfRegistered>
        {selfRegistered}
      </selfRegistered>
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
  }

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

  override def toString = "User [ id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", activationSalt=" + activationSalt +", street1=" + address.street1 + "]"
}
object User extends User with MetaDomain[User] with MetaMegaBasicUser[User] with FullEquality with Conversion {
  private var rand = new Random()

  def fromXml(xml: NodeSeq): User = {
    val user = new User()
    user.id = xml \ "id"
    user.password = xml \ "password"
    user.firstname = xml \ "firstname"
    user.lastname = xml \ "lastname"
    user.email = xml \ "email"
    user.password = xml \ "password"
    user.activationSalt = xml \ "activationSalt"
    user.selfRegistered = xml \ "selfRegistered"
    user.validated = xml \ "validated"

    val address = new Address
    user.address = address
    user.address.street1 = xml \ "address" \ "street1"
    user.address.street2 = xml \ "address" \ "street2"
    user.address.zip = xml \ "address" \ "zip"
    user.address.city = xml \ "address" \ "city"
    user.address.countryCode = xml \ "address" \ "country_code"

    user
  }

  def newActivationSalt: Long = rand.nextLong

}
}
}