package ch.plannr.model

import javax.persistence._

/**
 * User: Raffael Schmid
 *
 * TODO
 */

@Entity
class Role extends ToString {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(unique = true,nullable = false)
  var rolename: String = ""
}