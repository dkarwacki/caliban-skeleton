package customers.controllers

import caliban._
import cats.effect.IO
import customers.services.CustomerService
import io.circe.syntax._
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import utils.CatsGraphQLImplicits._
import utils.Generators
import zio.Runtime

import scala.concurrent.ExecutionContext

class CustomerControllerTest extends AnyWordSpec with Matchers {

  "A CustomerController" should {
    "return all customers" in new CustomerControllerFixture {
      //given
      defaultMockResults()

      val query =
        """query {
            customers {
              id,
              name,
              lastname,
              age,
              address {
                id,
                street,
                zip,
                city,
                country
              }
            }
          }"""

      //when
      val result =
        target.interpreter
          .unsafeRunSync()
          .executeAsync[IO](query)
          .unsafeRunSync()

      //then
      result.data.asJson.hcursor
        .downField("customers")
        .focus should contain(customers.asJson)
    }

    "return customer name by id" in new CustomerControllerFixture {
      //given
      defaultMockResults()

      val query =
        s"""query {
            customerById(id: ${customers.head.id}) {
              name
            }
          }"""

      //when
      val result =
        target.interpreter
          .unsafeRunSync()
          .executeAsync[IO](query)
          .unsafeRunSync()

      //then
      result.data.asJson.hcursor
        .downField("customerById")
        .downField("name")
        .focus should contain(customers.head.name.asJson)
    }

    "add new customer and return its name" in new CustomerControllerFixture {
      //given
      defaultMockResults()

      val query =
        """mutation AddCustomer($customer:CustomerInput!){
              addCustomer(customer: $customer) {
                name
              }
            }"""

      val variables = Map(
        "customer" -> customers.head.asJson.as[InputValue].toOption.get
      )

      //when
      val result =
        target.interpreter
          .unsafeRunSync()
          .executeAsync[IO](
            query = query,
            variables = variables
          )
          .unsafeRunSync()

      //then
      result.data.asJson.hcursor
        .downField("addCustomer")
        .downField("name")
        .focus should contain(customers.head.name.asJson)
    }
  }
}

trait CustomerControllerFixture extends Generators with MockFactory {
  implicit val runtime: Runtime[Any] = Runtime.default
  implicit val contextShift = IO.contextShift(ExecutionContext.global)

  //given
  val customers = List(customerGen.one(), customerGen.one(), customerGen.one())

  //mocks
  val customerService = stub[CustomerService]

  //mocks defaults
  def defaultMockResults() = {
    (customerService.all _).when().returns(IO(customers))
    (customerService.byId _).when(*).returns(IO(customers.headOption))
    (customerService.add _).when(*).returns(IO(customers.head))
  }

  lazy val target = new CustomerController(customerService)
}
