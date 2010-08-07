package ch.plannr.testing

import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.Server
import org.mortbay.jetty.nio.SelectChannelConnector

/**
 * Created by IntelliJ IDEA.
 * User: rschmid
 * Date: Jul 28, 2010
 * Time: 11:10:19 PM
 * To change this template use File | Settings | File Templates.
 */

object SingletonServer {
  def port = 9999
  def url = "localhost"

  def baseUrl = "http://" + url + ":" + port

  var server: Server = new Server

  var initialized = false

  def startup = {
    if (!initialized) {
      println("starting down server")
      val scc = new SelectChannelConnector
      scc.setPort(port)
      server.setConnectors(Array(scc))

      val context = new WebAppContext()
      context.setServer(server)
      context.setContextPath("/")
      context.setWar("src/main/webapp")

      server.addHandler(context)
      server.start()
      initialized = true
    }
  }

  def shutdown = {
    if (initialized) {
      println("shutting down server")
      try {
        server.stop()
        server.join()
      }
      catch {
        case exc: Exception => {}
      }
      finally {
        initialized = false
      }
    }
  }
}