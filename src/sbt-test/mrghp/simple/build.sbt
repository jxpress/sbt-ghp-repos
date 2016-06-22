import MavenRepositoryOnGitHubPlugin._

version := "0.0.1"

scalaVersion := "2.11.7"

organization := "com.gmail.dyamah"

name := "xxx"



enablePlugins(MavenRepositoryOnGitHubPlugin)

mavenRepositoryOnGitHub := "git@github.com:dyamah/repos.git"

