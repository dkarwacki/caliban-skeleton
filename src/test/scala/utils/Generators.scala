package utils

import customers.models.{Address, AddressId, Customer, CustomerId}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

trait Generators {
  val customerIdGen = Gen.posNum[Long].map(CustomerId(_))
  val addressIdGen = Gen.posNum[Long].map(AddressId(_))

  val addressGen: Gen[Address] = for {
    id <- addressIdGen
    street <- Gen.alphaStr
    zip <- Gen.alphaStr
    city <- Gen.alphaStr
    country <- Gen.alphaStr
  } yield Address(
    id = id,
    street = street,
    zip = zip,
    city = city,
    country = country
  )

  val customerGen: Gen[Customer] = for {
    id <- customerIdGen
    name <- Gen.alphaStr
    lastName <- Gen.alphaStr
    age <- Gen.posNum[Int]
    address <- addressGen
  } yield Customer(id, name, lastName, age, address)

  implicit class GenOpt[T](gen: Gen[T]) {
    def one(): T = gen.pureApply(Gen.Parameters.default, Seed.random(), 1000)
    def take(n: Int): List[T] = List.fill(n)(one())
  }
}
