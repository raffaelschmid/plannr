package ch.plannr.model

import javax.persistence._
import ch.plannr.common.persistence.{Domain, Persistent}

/**
 * User: Raffael Schmid
 *
 * TODO
 */

@Entity
@Table(name = "TBL_ROLE")
class Role extends Domain  with Persistent{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(unique = true,nullable = false)
  var rolename: String = ""
}