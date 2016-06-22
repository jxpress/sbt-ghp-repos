package net.jxpress.sbt.plugins

import sbt._
import Keys._
import language.postfixOps

object MvnReposOnGitHubPlugin extends AutoPlugin {

  lazy val cloneGHPages = taskKey[File]("checkout gh-pages.")


  object autoImport {
    val release = taskKey[Unit]("release the artifact to the repository on GitHub")
    lazy val mavenRepositoryName = settingKey[String]("maven repository name on GitHub.")
    lazy val gitHubURI = settingKey[String]("URI to maven repository on github.")
    val temporaryDir = settingKey[File]("temporary directory for git clone. (default target/tmp)")
  }

  import autoImport._


  private lazy val temporaryRepos = settingKey[File]("")

  override lazy val projectSettings = Seq(
    temporaryDir := target.value / "tmp",
    temporaryRepos := temporaryDir.value / mavenRepositoryName.value,
    publishTo := Some(Resolver.file("repos", temporaryRepos.value / name.value)),
    cloneGHPages := cloneTask.value,
    release := releaseTask().value
  )

  private def cloneTask = Def.task {
    val log = streams.value.log
    log.info("clone gh-pages")

    IO.createDirectory(temporaryDir.value)

    scala.sys.process.Process(s"git clone --branch gh-pages ${gitHubURI.value}", temporaryDir.value) !

    temporaryDir.value / name.value
  }

  private def releaseTask() = Def.task {
    cloneGHPages.value
    publish.value
    val log = streams.value.log
    log.info("push this artifact to gh-pages")

    scala.sys.process.Process("git config --global push.default matching", temporaryRepos.value) !!

    scala.sys.process.Process("git add .", temporaryRepos.value) !!

    val commitMessage = s"release-${name.value}-${version.value}"
    scala.sys.process.Process(s"""git commit -m '$commitMessage' """, temporaryRepos.value) !!

    scala.sys.process.Process("git push", temporaryRepos.value) !!
  }
}
