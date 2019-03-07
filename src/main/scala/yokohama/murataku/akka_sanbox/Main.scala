package yokohama.murataku.akka_sanbox

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main extends App with RequestTimeout {
  val host = "0.0.0.0"
  val port = 5000

  implicit val system: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val api: Route = new RestApi(system, requestTimeout()).routes

  implicit val materialier: ActorMaterializer = ActorMaterializer()

  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(api, host, port)

  LoggerFactory.getLogger(getClass).info("startup completed")
}

trait RequestTimeout {

  import scala.concurrent.duration._

  def requestTimeout(): Timeout = {
    val d = Duration("3s")
    FiniteDuration(d.length, d.unit)
  }
}
