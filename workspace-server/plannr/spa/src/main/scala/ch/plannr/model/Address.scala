package ch.plannr.model

import javax.persistence.{AccessType, Column, Embeddable, Access}
import org.hibernate.validator.constraints.{Length, NotEmpty}
import javax.validation.constraints.Size

/**
 * User: Raffael Schmid
 *
 * TODO
 */
@Embeddable
@Access(AccessType.FIELD)
class Address {
  @Column(nullable = false)
  @NotEmpty
  var street1: String = ""

  var street2: String = ""

  @Column(nullable = false)
  @Size(min = 4, max = 10)
  var zip: Int = _

  @Column(nullable = false)
  @NotEmpty
  var city: String = ""


}