package ch.plannr.common.http

import javax.servlet._
import http.{HttpServletRequest, HttpServletResponse}

/**
 * User: Raffael Schmid
 *
 * TODO
 */
class RequestWrapperFilter extends Filter {
  def init(config: FilterConfig) {}

  def destroy {}

  def doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) = {
    (req, res) match {
      case (httpReq: HttpServletRequest, httpRes: HttpServletResponse) => {
        chain.doFilter(new WrappedRequest(httpReq), new WrappedResponse(httpRes))
      }
      case _ => chain.doFilter(req, res)
    }
  }
}