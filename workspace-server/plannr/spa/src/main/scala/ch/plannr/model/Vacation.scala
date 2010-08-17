package ch.plannr.model

import javax.persistence._
import javax.validation.constraints.NotNull

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Entity
@Table(name = "TBL_VACATION")
class Vacation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "TITLE", nullable = false)
  @NotNull
  var title: String = _

  @Column(name = "DESCRIPTION")
  var description: String = _

  @ManyToOne
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  var user: User = _


}