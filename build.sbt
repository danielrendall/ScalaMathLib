import Dependencies._

// TODO - Scala 3 support (needs the dependent libraries to catch up...)
lazy val scala212 = "2.12.18"
lazy val scala213 = "2.13.15"
lazy val supportedScalaVersions = List(scala213, scala212)


Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion     := scala213
ThisBuild / organization     := "uk.co.danielrendall"
ThisBuild / organizationName := "scalamathlib"

githubOwner := "danielrendall"
githubRepository := "ScalaMathLib"
githubTokenSource := TokenSource.Environment("GITHUB_TOKEN")
releaseCrossBuild := true

lazy val root = (project in file("."))
  .settings(
    name := "ScalaMathLib",
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies ++= Seq(
      specs2 % Test,
      scalaCheck % Test
    )
  )

Compile / sourceGenerators += Def.task {
  val sourceDir = (Compile / sourceManaged).value
  CodeGen.writeFiles(sourceDir)
}.taskValue

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
