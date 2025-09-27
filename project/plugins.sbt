val sbtTypelevelVersion = "0.8.0"
addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.20.1")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.8")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.3.2")
addSbtPlugin("com.armanbilge" % "sbt-scala-native-config-brew-github-actions" % "0.4.0")
addSbtPlugin("io.github.sbt-doctest" % "sbt-doctest" % "0.12.2")
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.7.2")

libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always
