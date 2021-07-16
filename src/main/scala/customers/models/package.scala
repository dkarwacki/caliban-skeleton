package customers

import caliban.schema.{ArgBuilder, Schema}
import caliban.CalibanError.ExecutionError
import caliban.Value.IntValue.{BigIntNumber, IntNumber, LongNumber}
import cats.implicits.catsSyntaxEitherId
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype

package object models {

  @newtype case class CustomerId(value: Long)
  object CustomerId {
    implicit val argsBuilder: ArgBuilder[CustomerId] = {
      case IntNumber(value)    => CustomerId(value).asRight
      case LongNumber(value)   => CustomerId(value).asRight
      case BigIntNumber(value) => CustomerId(value.toLong).asRight
      case other               => ExecutionError(s"Cannot parse $other as customerId").asLeft
    }
    implicit val schema: Schema[Any, CustomerId] =
      Schema.longSchema.contramap(_.value)
    implicit val decoder: Decoder[CustomerId] =
      Decoder.decodeLong.map(CustomerId.apply)
    implicit val encoder: Encoder[CustomerId] =
      Encoder.encodeLong.contramap(_.value)
  }

  @newtype case class AddressId(value: Long)
  object AddressId {
    implicit val argsBuilder: ArgBuilder[AddressId] = {
      case IntNumber(value)    => AddressId(value).asRight
      case LongNumber(value)   => AddressId(value).asRight
      case BigIntNumber(value) => AddressId(value.toLong).asRight
      case other               => ExecutionError(s"Cannot parse $other as addressId").asLeft
    }
    implicit val schema: Schema[Any, AddressId] =
      Schema.longSchema.contramap(_.value)
    implicit val decoder: Decoder[AddressId] =
      Decoder.decodeLong.map(AddressId.apply)
    implicit val encoder: Encoder[AddressId] =
      Encoder.encodeLong.contramap(_.value)
  }
}
