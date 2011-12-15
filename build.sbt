name := "Trial"

organization := "me.remi.Trial"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")

resolvers ++= Seq(
    "Spot Archiva" at "http://infra.alpha.spotinfluence.com:8080/archiva/repository/spotinfluence.local/"
)

libraryDependencies ++= Seq(
        "com.shorrockin" %% "cascal" % "2.0-si-SNAPSHOT",
        // testing
        "junit" % "junit" % "4.8.1" % "test",
        "org.specs2" %% "specs2" % "1.6.1" % "test",
        "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
        "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"
)

publishMavenStyle := true

credentials ++= Seq(
    Credentials(Path.userHome / ".m2" / "credentials.archiva.internal"),
    Credentials(Path.userHome / ".m2" / "credentials.archiva.snapshots")
)

//logLevel := Level.Debug

//ivyLoggingLevel := UpdateLogging.Full
