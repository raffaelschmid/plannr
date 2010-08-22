package ch.plannr.common

import xml.NodeSeq
import java.text.SimpleDateFormat
import java.util.Date
import javax.persistence.Transient

/**
 * User: Raffael Schmid
 *
 * TODO
 */
trait Conversion {
  implicit def extract(s: NodeSeq): String = {
    val value = s.text.trim
    if (value.isEmpty) null else value
  }

  implicit def string2Long(ns: NodeSeq): Long = {
    val s = extract(ns)
    if (s != null) s.toLong else 0
  }

  implicit def string2Int(ns: NodeSeq): Int = {
    val s = extract(ns)
    if (s != null) s.toInt else 0
  }

  implicit def string2Boolean(ns: NodeSeq): Boolean = {
    val s = extract(ns)
    if (s != null) s.toBoolean else false
  }

  @Transient
  private val dateFormatString = "yyyyMMdd"

  implicit def string2Date(strDate: String): Date = new SimpleDateFormat(dateFormatString).parse(strDate)

  implicit def node2Date(nodeDate: NodeSeq): Date = {
    val strDate = extract(nodeDate)

    val retVal = if (strDate != null) new SimpleDateFormat(dateFormatString).parse(strDate) else null
    retVal
  }

  implicit def date2String(date: Date): String = {
    val retVal = if (date != null) new SimpleDateFormat(dateFormatString).format(date) else null
    retVal
  }
}