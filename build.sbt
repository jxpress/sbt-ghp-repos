scalaVersion  := "2.10.4"
organization  := "net.jxpress"
name          := "sbt-ghp-repos"
version       := "0.0.1"
sbtPlugin     := true
scalacOptions ++= Seq("-feature", "-deprecation")

ScriptedPlugin.scriptedSettings
scriptedBufferLog  := false
scriptedLaunchOpts <+= version { "-Dplugin.version=" + _ }
watchSources       <++= sourceDirectory map { path => (path ** "*").get }

publishMavenStyle := true
publishArtifact in (Compile, packageBin) := true
publishArtifact in (Compile, packageDoc) := false
publishArtifact in (Compile, packageSrc) := false
publishArtifact in Test := false

pomIncludeRepository := { _ => false}

// resolvers += "Maven Repository on Github" at "http://jxpress.github.io/mvnrepos/"
// libraryDependencies += "net.jxpress" % "kuromoji4s_2.11" % "0.0.3"
// publishTo := Some(Resolver.file("repos", target.value / "/tmp/mvnrepos/sbt-ghp-repos/" ))
