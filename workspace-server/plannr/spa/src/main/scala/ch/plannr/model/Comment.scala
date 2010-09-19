package ch.plannr.model

import javax.persistence._
import javax.validation.constraints.{Size, NotNull}
import java.util.Date

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Entity
@Table(name = "TBL_COMMENT")
class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  var user: User = _

  @ManyToOne
  @JoinColumn(name = "VACATION_ID", referencedColumnName = "ID")
  var vacation: Vacation = _

  @Column(name = "COMMENT", nullable = false)
  @NotNull
  @Size(min = 1, max = 4000)
  var comment: String = _

  @Temporal(TemporalType.TIME)
  @Column(name = "PUBLISH_DATE", nullable = false)
  @NotNull
  var publishDate: Date = new Date()


}