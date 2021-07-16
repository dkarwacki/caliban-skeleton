package customers.models
import io.circe._
import io.circe.generic.semiauto._

case class Address(
    id: AddressId,
    street: String,
    zip: String,
    city: String,
    country: String
)

object Address {
  implicit val decoder: Decoder[Address] = deriveDecoder[Address]
  implicit val encoder: Encoder[Address] = deriveEncoder[Address]
}
