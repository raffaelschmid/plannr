import sbt._

class Backend(info: ProjectInfo) extends DefaultWebProject(info) {
  // comment out if you're not also using maven
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
  //  val jbossRepository = "JBoss Repository" at "http://repository.jboss.org/maven2"
  val scalaReleases = "Scala Releases" at "http://scala-tools.org/repo-releases"

  val liftVersion = "2.1-SNAPSHOT"

  override def libraryDependencies = Set(
    "mysql"%"mysql-connector-java" % "5.1.13" % "compile->default",
    //"jaxb" % "jaxb-api" % "2.1.5" % "compile->default",
    "net.liftweb" % "lift-webkit_2.8.0" % liftVersion % "compile->default",
    "net.liftweb" % "lift-jpa_2.8.0" % liftVersion % "compile->default",
    "org.hibernate" % "hibernate-entitymanager" % "3.5.1-Final" % "compile->default",
    "org.hibernate" % "hibernate-validator" % "4.1.0.Final" % "compile->default",
    "geronimo-spec" % "geronimo-spec-ejb" % "2.1-rc4" % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "compile->default",
    "com.h2database" % "h2" % "1.2.121",
    "junit" % "junit" % "4.5" % "compile->default",
    "org.scala-tools.testing" % "specs_2.8.0" % "1.6.5" % "test->default",
    "org.slf4j" % "slf4j-log4j12" % "1.4.1",
    "ch.plannr" % "plannr-test" % "1.0-SNAPSHOT" % "test->default"
    ) ++ super.libraryDependencies

  // required because Ivy doesn't pull repositories from poms
  // comment out if you're not also using maven
  val smackRepo = "m2-repository-smack" at "http://maven.reucon.com/public"
  val jbossRepo = "m2-jboss-repo" at "https://repository.jboss.org/nexus/content/repositories/public"

}
