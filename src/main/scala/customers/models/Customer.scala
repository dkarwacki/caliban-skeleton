package customers.models

import io.circe._, io.circe.generic.semiauto._

case class Customer(
    id: CustomerId,
    name: String,
    lastname: String,
    age: Long,
    address: Address
)

object Customer {
  implicit val decoder: Decoder[Customer] = deriveDecoder[Customer]
  implicit val encoder: Encoder[Customer] = deriveEncoder[Customer]
}
