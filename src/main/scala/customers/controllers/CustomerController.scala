package customers.controllers

import caliban._
import caliban.GraphQL.graphQL
import cats.effect.IO
import customers.services.CustomerService
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import utils.CatsGraphQLImplicits._
import zio.Runtime

class CustomerController(customerService: CustomerService)(implicit
    runtime: Runtime[Any]
) {

  private val graphQLApi = graphQL(
    RootResolver(
      queryResolver = CustomerQueries(customerService),
      mutationResolver = CustomerMutations(customerService)
    )
  )

  val interpreter = graphQLApi.interpreterAsync[IO]

  val routes = interpreter
    .map(Http4sAdapter.makeHttpServiceF[IO, CalibanError](_))
    .map(routes =>
      Router(
        "/api/graphql" -> routes
      ).orNotFound
    )
}
