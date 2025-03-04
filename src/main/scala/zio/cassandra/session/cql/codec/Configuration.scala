package zio.cassandra.session.cql.codec

import zio.cassandra.session.cql.codec.Configuration.FieldNamesTransformation

import java.util.regex.Pattern

final case class Configuration(fieldNamesTransformation: FieldNamesTransformation) {

  val transformFieldNames: String => String = fieldNamesTransformation match {
    case Configuration.NoTransformation                     => identity
    case Configuration.Transformation(transformation)       => transformation
    case Configuration.CachedTransformation(transformation) =>
      val cache = new java.util.concurrent.ConcurrentHashMap[String, String]()

      fieldName =>
        if (cache.containsKey(fieldName)) cache.get(fieldName)
        else {
          val res = transformation(fieldName)
          cache.putIfAbsent(fieldName, res)
          res
        }
  }

}

object Configuration {

  def apply(transformFieldNames: String => String): Configuration =
    Configuration(CachedTransformation(transformFieldNames))

  sealed trait FieldNamesTransformation
  case object NoTransformation                                            extends FieldNamesTransformation
  final case class Transformation(transformation: String => String)       extends FieldNamesTransformation
  final case class CachedTransformation(transformation: String => String) extends FieldNamesTransformation

  implicit lazy val defaultConfiguration: Configuration = Configuration(CachedTransformation(snakeCaseTransformation))

  private val basePattern: Pattern = Pattern.compile("([A-Z]+)([A-Z][a-z])")
  private val swapPattern: Pattern = Pattern.compile("([a-z\\d])([A-Z])")

  val snakeCaseTransformation: String => String = s => {
    val partial = basePattern.matcher(s).replaceAll("$1_$2")
    swapPattern.matcher(partial).replaceAll("$1_$2").toLowerCase
  }

}
