package ch.plannr.model

import javax.validation.constraints.{Size, NotNull}
import javax.persistence._
import xml.Node
import ch.plannr.common.{Conversion, FullEquality}
import reflect.BeanProperty
import ch.plannr.common.persistence.{DBModel, Persistent, Domain}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Entity
@Table(name = "TBL_TEAM")
class Team extends Domain with Persistent[Team] {
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


  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
  @BeanProperty
  var owner: User = _

  @ManyToMany(mappedBy = "memberOf")
  var members: _root_.java.util.Set[User] = new _root_.java.util.HashSet[User]()

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
    </team>

  override def toString = "Team [ id=" + id + ", name=" + name + ", description=" + description + "]"
}

object Team extends Team with FullEquality with Conversion {
  def fromXml(xml: Node): Team = {
    val team = new Team()
    team.id = xml \ "id"
    team.name = xml \ "name"
    team.description = xml \ "description"
    team
  }

  def findById(id: Long) = {
    DBModel.find(classOf[Team], id)
  }
}