lazy val root = (project in file(".")).
  settings(
    name := "StockApp",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test"
