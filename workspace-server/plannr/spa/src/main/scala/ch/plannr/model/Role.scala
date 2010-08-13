package ch.plannr.model

import javax.persistence._
import ch.plannr.common.persistence.Persistent

/**
 * User: Raffael Schmid
 *
 * TODO
 */

@Entity
class Role extends ToString  with Persistent{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(unique = true,nullable = false)
  var rolename: String = ""
}