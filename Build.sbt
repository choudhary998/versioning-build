import Dependencies._
import NativePackagerHelper._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "automated-fargate-poc",
    libraryDependencies += scalaTest % Test,
    packageName in Universal := s"${name.value}-${version.value}",
    topLevelDirectory := Some(name.value),
    mappings in Universal ++= directory(s"${baseDirectory.value}/src/main/resources")
  ).enablePlugins(JavaAppPackaging)
© 2020 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
Pricing
API
Training
Blog
About
