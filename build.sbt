name := """playStart"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

resolvers += "MicroAd release repository" at "http://192.168.196.193:8081/nexus/content/repositories/releases/"
libraryDependencies ++= Seq(
  "jp.microad.kyoto" %% "c3po-persistence-jdbc-dao" % "1.0.0",
  "jp.microad.kyoto" %% "c3po-pool" % "1.0.1",
  "jp.microad.kyoto" %% "c3po-config" % "1.0.1",
  "jp.microad.kyoto" %% "c3po-persistence-jdbc" % "1.2.0",
  "org.specs2" %% "specs2-core" % "3.8.9"
)