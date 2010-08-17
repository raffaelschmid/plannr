package ch.plannr.model

import javax.validation.constraints.{Size, NotNull}
import javax.persistence._

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Entity
@Table(name = "TBL_TEAM")
class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "NAME", nullable = false)
  @NotNull
  @Size(min = 5, max = 50)
  var name: String = _

  @Column(name = "DESCRIPTION", nullable = false)
  @NotNull
  @Size(min = 20, max = 200)
  var description: String = _


  @ManyToMany(mappedBy = "teams")
  var members: _root_.java.util.Set[User] = new _root_.java.util.HashSet[User]()

}