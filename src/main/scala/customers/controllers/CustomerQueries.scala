package customers.controllers

import cats.effect.IO
import customers.models.{Customer, CustomerId}
import customers.services.CustomerService

object CustomerQueries {
  case class CustomerIdInput(id: CustomerId)

  case class Queries(
      customers: IO[List[Customer]],
      customerById: CustomerIdInput => IO[Option[Customer]]
  )

  def apply(customerService: CustomerService): Queries =
    Queries(
      customers = customerService.all(),
      customerById = input => customerService.byId(input.id)
    )
}
