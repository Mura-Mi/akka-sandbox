package yokohama.murataku.akka_sanbox.model.game

import akka.actor.{Actor, Props}
import akka.util.Timeout

case class Inning(count: Int, side: Side) {
  def increment(): Inning = side match {
    case Side.Top => this.copy(side = Side.Bottom)
    case Side.Bottom => Inning(count = this.count + 1, side = Side.Top)
  }
}

object Inning {
  val initial = Inning(1, Side.Top)
}

sealed trait Side

object Side {
  case object Top extends Side
  case object Bottom extends Side
}


class Game(implicit timeout: Timeout) extends Actor {
  import Game._

  var outCount: Int = 0

  var inning: Inning = Inning.initial

  //noinspection TypeAnnotation
  def receive = {
    case HitterOut =>sender() ! hitterOut()
    case ShowGame => sender() ! Some(GameOutline(outCount, inning))
  }

  def hitterOut() : Result=  {
    val before = this.inning
    if (before == Inning(9, Side.Bottom) && this.outCount == 3) gameAlreadyFinished
    else {
      this.outCount += 1
      if(this.outCount == 3) {
        this.outCount = 0
        this.inning = before.increment()
      }
      success
    }
  }

}

object Game {

  def props(implicit timeout: Timeout) = Props(new Game())

  case class GameOutline(outCount: Int, inning: Inning)

  case object HitterOut

  case object ShowGame

  case class Result(value: String)

  val success = Result("success")

  val gameAlreadyFinished = Result("game already finished")
}
