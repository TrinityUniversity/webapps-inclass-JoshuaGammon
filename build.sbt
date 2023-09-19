import sbtcrossproject.{crossProject, CrossType}

enablePlugins(JavaAppPackaging)

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val server = (project in file("server")).settings(commonSettings).settings(
	name := "play-server",
  scalaJSProjects := Seq(client),
  Assets / pipelineStages := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.2.0",
    "com.google.inject"            % "guice"                % "6.0.0",
    "com.google.inject.extensions" % "guice-assistedinject" % "6.0.0",
    guice,
		"org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
		"com.typesafe.play" %% "play-slick" % "5.1.0",
		"com.typesafe.slick" %% "slick-codegen" % "3.4.1",
    "org.postgresql" % "postgresql" % "42.6.0",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
    specs2 % Test
  ),
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
	name := "play-client",
  scalacOptions += "-Ymacro-annotations",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "2.6.0",
		"me.shadaj" %%% "slinky-core" % "0.7.4",
		"me.shadaj" %%% "slinky-web" % "0.7.4"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
	.settings(
		name := "play-shared"
	)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.13.11",
  organization := "edu.trinity",
  libraryDependencies += "com.typesafe.play" %%% "play-json" % "2.9.4"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
