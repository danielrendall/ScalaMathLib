import Dependencies._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "uk.co.danielrendall"
ThisBuild / organizationName := "scalamathlib"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaMathLib",
    libraryDependencies ++= Seq(
      specs2 % Test,
      scalaCheck % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
