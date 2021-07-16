package customers

import cats.effect.{ContextShift, IO}
import customers.controllers.CustomerController
import customers.services.CustomerServiceImpl
import zio.Runtime

class CustomerModule(implicit runtime: Runtime[Any], cs: ContextShift[IO]) {
  lazy val service = new CustomerServiceImpl()
  lazy val controller = new CustomerController(service)
}
