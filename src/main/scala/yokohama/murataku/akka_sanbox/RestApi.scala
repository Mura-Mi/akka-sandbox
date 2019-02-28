package yokohama.murataku.akka_sanbox

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import yokohama.murataku.akka_sanbox.model.game.Game
import yokohama.murataku.akka_sanbox.model.game.Game.Result

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}




class RestApi(system: ActorSystem, timeout: Timeout) extends RestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher

  override def initGame(): ActorRef = system.actorOf(Game.props, "new-game")
}

trait RestRoutes extends GameApi with ModelMarshalling {

  import akka.http.scaladsl.model.StatusCodes._

  def routes: Route = pingRoute ~ gameRoute

  def pingRoute: Route = pathPrefix("ping") {
    pathEndOrSingleSlash {
      get {
        complete(OK, "pong")
      }
    }
  }

  def gameRoute: Route = pathPrefix("game") {
    pathEndOrSingleSlash {
      get {
        onSuccess(showGame()) { maybe =>
          maybe.fold(complete(NotFound))(e => complete(e))
        }
      }
    } ~ pathPrefix("strikeout") {
      pathEndOrSingleSlash {
        post {
          onSuccess(incrementOutCount()) { result =>
            complete(result)
          }
        }
      }
    }
  }
}

trait GameApi {
  lazy val game: ActorRef = initGame()
  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  def initGame(): ActorRef

  def showGame(): Future[Option[Game.GameOutline]] = game.ask(Game.ShowGame).mapTo[Option[Game.GameOutline]]

  def incrementOutCount(): Future[Result] = game.ask(Game.HitterOut).mapTo[Result]
}

