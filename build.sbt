enablePlugins(JavaServerAppPackaging)

name := "akka-sandbox"

version := "1.0"

organization := "yokohama.murataku" 

libraryDependencies ++= {
  val akkaVersion = "2.5.20"
  val akkaHttpVersion = "10.1.7"
  val enumeratumVersion = "1.5.13"
  val circeVersion = "0.11.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-stream"     % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core"  % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http"       % akkaHttpVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.25.2",
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.3",
    "com.beachape"      %% "enumeratum"      % enumeratumVersion,
    "io.circe"          %% "circe-core"      % circeVersion,
    "io.circe"          %% "circe-generic"   % circeVersion,
    "io.circe"          %% "circe-parser"    % circeVersion,
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion   % "test",
    "org.scalatest"     %% "scalatest"       % "3.0.0"       % "test"
  )
}
