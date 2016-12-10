lazy val root = (project in file(".")).
  settings(
    name := "StockApp",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

libraryDependencies += "joda-time" % "joda-time" % "2.9.6"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.24"
