package yokohama.murataku.akka_sanbox.model.game

import akka.actor.{Actor, Props}
import akka.util.Timeout

class Game(implicit timeout: Timeout) extends Actor {
  import Game._

  var outCount: Int = 0

  //noinspection TypeAnnotation
  def receive = {
    case HitterOut =>
      outCount = (outCount + 1) % 3
    case ShowGame => sender() ! Some(GameOutline(outCount))
  }

}

object Game {

  def props(implicit timeout: Timeout) = Props(new Game())

  case class GameOutline(outCount: Int)

  case object HitterOut

  case object ShowGame

}
