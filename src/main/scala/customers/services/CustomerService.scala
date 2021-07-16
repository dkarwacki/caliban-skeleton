package customers.services

import cats.effect.IO
import customers.models.{Address, AddressId, Customer, CustomerId}

trait CustomerService {
  def add(customer: Customer): IO[Customer]
  def all(): IO[List[Customer]]
  def byId(id: CustomerId): IO[Option[Customer]]
}

class CustomerServiceImpl extends CustomerService {
  val Customers = List(
    Customer(
      CustomerId(1),
      "John",
      "Doe",
      34,
      Address(AddressId(1), "Baker Street 23", "44-555", "London", "England")
    ),
    Customer(
      CustomerId(2),
      "Jessica",
      "Parker",
      22,
      Address(AddressId(2), "Downing Street 93", "11-434", "London", "England")
    )
  )

  override def add(customer: Customer): IO[Customer] = IO(customer)

  override def all(): IO[List[Customer]] = IO(Customers)

  override def byId(id: CustomerId): IO[Option[Customer]] =
    IO(Customers.find(_.id == id))
}
