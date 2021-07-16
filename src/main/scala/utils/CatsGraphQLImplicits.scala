package utils

import caliban.CalibanError
import caliban.schema.Schema
import cats.effect.IO

object CatsGraphQLImplicits {
  import caliban.interop.cats.implicits._
  implicit val ioInterpreter = CatsEffectGraphQLInterpreter[Any, CalibanError] _
  implicit val ioGraphQL = CatsEffectGraphQL[Any, CalibanError] _
  implicit def ioSchema[A](implicit ev: Schema[Any, A]): Schema[Any, IO[A]] =
    catsEffectSchema[IO, Any, A] _
}
