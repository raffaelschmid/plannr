package ch.plannr.model

import javax.persistence._
import javax.validation.constraints.NotNull
import java.util.Date
import ch.plannr.common.Conversion
import xml.NodeSeq
import net.liftweb.common.Box
import ch.plannr.common.persistence.{DBModel, MetaDomain, Domain, Persistent}
import reflect.BeanProperty

@Entity
@Table(name = "TBL_VACATION")
class Vacation extends Domain with Persistent[Vacation] with Conversion {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "TITLE", nullable = false)
  @NotNull
  @BeanProperty
  var title: String = _

  @Column(name = "DESCRIPTION")
  @BeanProperty
  var description: String = _

  @ManyToOne
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  @BeanProperty
  var user: User = _

  @ManyToOne
  @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
  @BeanProperty
  var team: Team = _

  @Temporal(TemporalType.DATE)
  @Column(name = "FROM_DATE", nullable = false)
  @NotNull
  @BeanProperty
  var from: Date = _

  @Temporal(TemporalType.DATE)
  @Column(name = "TO_DATE", nullable = false)
  @NotNull
  @BeanProperty
  var to: Date = _

  @OneToMany(mappedBy = "vacation")
  @BeanProperty
  var ownerOf: _root_.java.util.Set[Comment] = new _root_.java.util.HashSet[Comment]

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
  override def fromXml(xml: NodeSeq) = {
    val retVal = new Vacation
    retVal.id = xml \ "id"
    retVal.title = xml \ "title"
    retVal.description = xml \ "description"
    retVal.from = xml \ "from"
    retVal.to = xml \ "to"
    retVal
  }

  def findById(id: Long): Box[Vacation] = {
    DBModel.find(classOf[Vacation], id)
  }
}

