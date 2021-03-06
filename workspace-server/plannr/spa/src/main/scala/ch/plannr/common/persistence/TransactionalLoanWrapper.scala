package ch.plannr.common.persistence

import net.liftweb.util.LoanWrapper

class TransactionalLoanWrapper extends LoanWrapper {
  def apply[T](f: => T): T = {
    try {
      f
    }
    catch {
      case e => DBModel.getTransaction.setRollbackOnly
      f
    }
    finally {
      DBModel.cleanup
    }
  }
}
