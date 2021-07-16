import cats.effect.{ExitCode, IO, IOApp}
import config.Configuration
import customers.CustomerModule
import org.http4s.server.blaze.BlazeServerBuilder
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import zio.{Runtime, ZEnv}

object Main extends IOApp {
  implicit val runtime: Runtime[ZEnv] = Runtime.default

  val httpConfiguration =
    ConfigSource.default.loadOrThrow[Configuration].httpConfiguration
  val customerModule = new CustomerModule()

  override def run(args: List[String]): IO[ExitCode] = {
    val program = for {
      routes <- customerModule.controller.routes
      _ <-
        BlazeServerBuilder[IO](executionContext)
          .bindHttp(httpConfiguration.port, httpConfiguration.host)
          .withHttpApp(routes)
          .serve
          .compile
          .drain
    } yield ()

    program.as(ExitCode.Success)
  }
}
