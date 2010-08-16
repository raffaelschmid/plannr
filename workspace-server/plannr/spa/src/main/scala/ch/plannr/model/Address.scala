package ch.plannr.model

import javax.persistence.{AccessType, Column, Embeddable, Access}
import org.hibernate.validator.constraints.{Length, NotEmpty}
import javax.validation.constraints.Size
import ch.plannr.common.ToString

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Embeddable
@Access(AccessType.FIELD)
class Address extends ToString{
  var street1: String = _

  var street2: String = _

  @Size(min = 4, max = 10)
  var zip: Int = _

  var city: String = _

  @Size(min = 3, max = 3)
  var countryCode: String = _


  /**
   * persistence provider needs carefully selected properties for equality check
   */
  override def equals(other: Any): Boolean =
    other match {
      case that: Address =>
        (that canEqual this) &&
                street1 == that.street1 &&
                street2 == that.street2 &&
                zip == that.zip &&
                city == that.city &&
                countryCode == that.countryCode
      case _ => false
    }

  /**
   * persistence provider needs carefully selected properties for calculating hashCode
   */
  override def hashCode: Int = 41 * (
          41 * (
                  41 * (
                          41 * (
                                  41 * (
                                          41 + hash(street1)
                                          )
                                          + hash(street2)
                                  )
                                  + hash(zip)
                          )
                          + hash(city)
                  )
                  + hash(countryCode)
          )

  def canEqual(other: Any): Boolean = other.isInstanceOf[Address]

  private def hash(o: Any) = if (o != null) o.hashCode else 0

}