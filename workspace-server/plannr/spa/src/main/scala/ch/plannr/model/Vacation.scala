package ch.plannr.model

import javax.persistence._
import javax.validation.constraints.NotNull
import java.util.Date
import ch.plannr.common.persistence.{MetaDomain, Domain, Persistent}
import xml.Node
import ch.plannr.common.Conversion
import java.text.SimpleDateFormat

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Entity
@Table(name = "TBL_VACATION")
class Vacation extends Domain with Persistent[Vacation] with Conversion {
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

  @ManyToOne
  @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
  var team: Team = _

  @Temporal(TemporalType.DATE)
  @Column(name = "FROM_DATE", nullable = false)
  @NotNull
  var from: Date = _

  @Temporal(TemporalType.DATE)
  @Column(name = "TO_DATE", nullable = false)
  @NotNull
  var to: Date = _

  override def toXml =
    <vacation>
      <id>
        {id}
      </id>
      <title>
        {title}
      </title>
      <description>
        {description}
      </description>
      <from>
        {date2String(from)}
      </from>
      <to>
        {date2String(to)}
      </to>
    </vacation>

  override def toString = "Vacation [ id=" + id + ", title=" + title + ", description=" + description + ", from=" + date2String(from) + ", to=" + date2String(to) + "]"

}

object Vacation extends Vacation with MetaDomain[Vacation] with Conversion {
  override def fromXml(xml: Node) = {
    val retVal = new Vacation
    retVal.id = xml \ "id"
    retVal.title = xml \ "title"
    retVal.description = xml \ "description"
    retVal.from = xml \ "from"
    retVal.to = xml \ "to"
    retVal
  }
}

