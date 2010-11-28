package ch.plannr.model

import javax.persistence._
import ch.plannr.common.persistence.{Domain, Persistent}


@Entity
@Table(name = "TBL_ROLE")
class Role extends Domain with Persistent[Role]{//  with Persistent[Role]{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="ID")
  var id: Long = _

  @Column(name="ROLE_NAME",unique = true,nullable = false)
  var rolename: String = ""
}