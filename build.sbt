name := "recRNN"

version := "0.1"

scalaVersion := "2.11.12"

resolvers += "ossrh repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

// set the main class for 'sbt run'
mainClass := Some("SeqRecommenderExp")

val sparkVersion = "2.4.0"
val bigDLVersion = "0.7.2"
val analyticsZooVersion = "0.4.0"
val scalaTestVersion = "3.0.5"
val mleapVersion = "0.12.0"
val awsJavaSdkVersion = "1.11.500"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)
libraryDependencies += "com.intel.analytics.zoo" % s"analytics-zoo-bigdl_$bigDLVersion-spark_$sparkVersion" % analyticsZooVersion
libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % mleapVersion
libraryDependencies += "ml.combust.mleap" %% "mleap-spark-extension" % mleapVersion
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % awsJavaSdkVersion
libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".proto" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".so" => MergeStrategy.first
  //  case PathList("org", "slf4j", xs@_*) => MergeStrategy.deduplicate
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case PathList("META-INF", "services", "org.apache.hadoop.fs.FileSystem") => MergeStrategy.filterDistinctLines
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}