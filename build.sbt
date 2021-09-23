import Dependencies._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / organization     := "uk.co.danielrendall"
ThisBuild / organizationName := "scalamathlib"

githubOwner := "danielrendall"
githubRepository := "ScalaMathLib"
githubTokenSource := TokenSource.Environment("GITHUB_TOKEN")

lazy val root = (project in file("."))
  .settings(
    name := "ScalaMathLib",
    libraryDependencies ++= Seq(
      specs2 % Test,
      scalaCheck % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
