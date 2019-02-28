package yokohama.murataku.akka_sanbox.model.game

import akka.actor.{Actor, Props}
import akka.util.Timeout

class Game(implicit timeout: Timeout) extends Actor {
  import Game._

  var outCount: Int = 0

  //noinspection TypeAnnotation
  def receive = {
    case HitterOut =>
      outCount += 1
      if (outCount >= 3) {
        outCount = 0
        sender() ! NextInning
      } else
        sender() ! InningContinue
    case ShowGame => sender() ! Some(GameOutline(outCount))
  }

}

object Game {

  def props(implicit timeout: Timeout) = Props(new Game())

  case class GameOutline(outCount: Int)

  case object HitterOut

  case object ShowGame

  case class Result(value: String)

  val NextInning = Result("next inning")

  val InningContinue = Result("continues")
}
