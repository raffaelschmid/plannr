package ch.plannr.common

import xml.NodeSeq

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
}