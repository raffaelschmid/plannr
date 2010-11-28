package ch.plannr.model

import javax.validation.constraints.{Size, NotNull}
import javax.persistence._
import ch.plannr.common.{Conversion}
import ch.plannr.common.persistence.{DBModel, Persistent, Domain}
import net.liftweb.common.Box
import scala.collection.JavaConversions._
import xml.NodeSeq


@Entity
@Table(name = "TBL_TEAM")
class Team extends Domain with Persistent[Team] {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "NAME", nullable = false)
  @NotNull
  @Size(min = 1, max = 50)
  var name: String = _

  @Column(name = "DESCRIPTION")
  @Size(max = 200)
  var description: String = _


  @ManyToOne
  @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
  var owner: User = _

  @ManyToMany(mappedBy = "memberOf", fetch = FetchType.EAGER)
  var members: _root_.java.util.Set[User] = new _root_.java.util.HashSet[User]()

  @OneToMany(mappedBy = "team")
  var vacations: _root_.java.util.Set[Vacation] = new _root_.java.util.HashSet[Vacation]

  override def toXml =
    <team>
      <id>
        {id}
      </id>
      <name>
        {name}
      </name>
      <description>
        {description}
      </description>
      <members>
        {for{m <- members; n = m.toXml} yield n}
      </members>
    </team>

  def example = {
    <user>
      <id>
        1
      </id>
    </user>
  }

  override def toString = "Team [ id=" + id + ", name=" + name + ", description=" + description + "]"
}

object Team extends Team with Conversion {
  def apply(name: String, description: String): Team = {
    val retVal = new Team
    retVal.name = name
    retVal.description = description
    retVal
  }

  def fromXml(xml: NodeSeq): Team = {
    val team = new Team()
    team.id = xml \ "id"
    team.name = xml \ "name"
    team.description = xml \ "description"
    team
  }

  def findById(id: Long): Box[Team] = {
    DBModel.find(classOf[Team], id)
  }
}