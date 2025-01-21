import com.typesafe.tools.mima.core._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / tlBaseVersion := "0.9"

ThisBuild / organization := "org.typelevel"
ThisBuild / organizationName := "Typelevel"
ThisBuild / startYear := Some(2023)

ThisBuild / crossScalaVersions := Seq("3.3.3", "2.12.20", "2.13.14")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.head

ThisBuild / doctestTestFramework := DoctestTestFramework.ScalaCheck

ThisBuild / developers ++= List(
  "mpilquist" -> "Michael Pilquist"
).map { case (username, fullName) =>
  tlGitHubDev(username, fullName)
}

ThisBuild / licenses := List(
  ("BSD-3-Clause", url("https://github.com/typelevel/twiddles/blob/main/LICENSE"))
)

ThisBuild / mimaBinaryIssueFilters ++= Seq(
)

lazy val root = tlCrossRootProject
  .aggregate(
    core
  )

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("core"))
  .settings(
    name := "twiddles-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.13.0",
      "org.scalameta" %%% "munit" % "1.0.1"
    ) ++ (if (scalaVersion.value.startsWith("2.")) Seq("com.chuusai" %%% "shapeless" % "2.3.12")
          else Nil),
    scalacOptions := scalacOptions.value.filterNot(_.startsWith("-source:"))
  )

lazy val coreJVM = core.jvm
  .settings(
    libraryDependencies ++=
      (if (scalaVersion.value.startsWith("2."))
         Seq(
           "org.scala-lang" % "scala-reflect" % scalaVersion.value % Test,
           "org.scala-lang" % "scala-compiler" % scalaVersion.value % Test
         )
       else Nil)
  )

lazy val coreJS = core.js
  .disablePlugins(DoctestPlugin)
  .settings(
    Test / scalaJSStage := FastOptStage,
    jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv(),
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )

lazy val coreNative = core.native
  .enablePlugins(ScalaNativeBrewedConfigPlugin)
  .disablePlugins(DoctestPlugin)
  .settings(
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "0.9.0").toMap
  )

lazy val docs = project
  .in(file("docs"))
  .enablePlugins(MdocPlugin)
  .dependsOn(coreJVM)
  .settings(
    mdocIn := baseDirectory.value / "src",
    mdocOut := baseDirectory.value / "..",
    githubWorkflowArtifactUpload := false
  )
