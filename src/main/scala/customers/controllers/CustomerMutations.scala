package customers.controllers

import cats.effect.IO
import customers.models.Customer
import customers.services.CustomerService

object CustomerMutations {
  case class CustomerInput(customer: Customer)

  case class Mutations(
      addCustomer: CustomerInput => IO[Customer]
  )

  def apply(customerService: CustomerService): Mutations =
    Mutations(addCustomer = input => customerService.add(input.customer))
}
