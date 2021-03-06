package ch.plannr.testing

import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.Server
import org.mortbay.jetty.nio.SelectChannelConnector
import java.io.File

/**
 * Created by IntelliJ IDEA.
 * User: rschmid
 * Date: Jul 28, 2010
 * Time: 11:10:19 PM
 * To change this template use File | Settings | File Templates.
 */

class SingletonServer {
}
object SingletonServer {
  def port = 9999

  def url = "localhost"

  def baseUrl = "http://" + url + ":" + port

  var server: Server = null

  var initialized = false

  def startup = {
    server = new Server
    println("starting server")
    val scc = new SelectChannelConnector
    scc.setPort(SingletonServer.port)
    server.setConnectors(Array(scc))

    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setWar("src/main/webapp")

    server.addHandler(context)
    server.start()
    new File("jettylock").createNewFile

  }

  def shutdown = {
      server.stop
      server.join
  }
}