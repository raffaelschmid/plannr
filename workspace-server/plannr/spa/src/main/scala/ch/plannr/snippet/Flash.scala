package ch.plannr.snippet

import xml.{Text, NodeSeq}
import net.liftweb.http.js.JsCmds.Run
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds._

class Flash {
  
  val headString = """
            var swfVersionStr = "10.0.0";
            var xiSwfUrlStr = "flex/playerProductInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "Main";
            attributes.name = "Main";
            attributes.align = "top";
            swfobject.embedSWF("flex/Main.swf", "flashContent", "100%", "100%", swfVersionStr, xiSwfUrlStr, flashvars, params, attributes);
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
  """

  def head(xhtml: NodeSeq): NodeSeq = bind("flash", xhtml,
    "object" -> Script(Run(headString))
    )
}