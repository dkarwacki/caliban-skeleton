lazy val catsVersion = "2.3.1"
lazy val http4sVersion = "0.21.22"
lazy val circeVersion = "0.12.3"
lazy val scalaTestVersion = "3.1.0"
lazy val scalaMockVersion = "5.1.0"
lazy val pureConfigVersion = "0.15.0"
lazy val logbackVersion = "1.2.3"
lazy val calibanVersion = "1.0.1"
lazy val newTypeVersion = "0.4.4"

lazy val catsDependencies = Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-effect" % catsVersion,
  "org.typelevel" %% "cats-effect-laws" % catsVersion % Test
)

lazy val scalaTestDependencies = Seq(
  "org.scalactic" %% "scalactic" % scalaTestVersion % Test,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test
)

lazy val pureConfigDependencies = Seq(
  "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
)

lazy val loggerDependencies = Seq(
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "ch.qos.logback" % "logback-core" % logbackVersion
)

lazy val circeDependencies = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion
)

lazy val http4sDependencies = Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion
)

lazy val calibanDependencies = Seq(
  "com.github.ghostdogpr" %% "caliban" % calibanVersion,
  "com.github.ghostdogpr" %% "caliban-http4s" % calibanVersion,
  "com.github.ghostdogpr" %% "caliban-cats" % calibanVersion
)

lazy val newTypeDependencies = Seq(
  "io.estatico" %% "newtype" % newTypeVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "caliban-skeleton",
    version := "0.1",
    organization := "com.dkarwacki",
    scalaVersion := "2.13.6",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= circeDependencies
      ++ catsDependencies
      ++ http4sDependencies
      ++ scalaTestDependencies
      ++ pureConfigDependencies
      ++ loggerDependencies
      ++ calibanDependencies
      ++ newTypeDependencies
  )
