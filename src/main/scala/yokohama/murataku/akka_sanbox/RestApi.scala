package yokohama.murataku.akka_sanbox

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor

class RestApi(system: ActorSystem, timeout: Timeout) extends RestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher
}

trait RestRoutes  {
  import akka.http.scaladsl.model.StatusCodes._

  def routes: Route = pingRoute

  def pingRoute: Route = pathPrefix("ping") {
    pathEndOrSingleSlash {
      get {
        complete(OK, "pong")
      }
    }
  }
}
