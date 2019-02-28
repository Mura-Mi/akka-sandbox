package yokohama.murataku.akka_sanbox

import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import yokohama.murataku.akka_sanbox.model.game.Game.{GameOutline, Result}

trait ModelMarshalling extends DefaultJsonProtocol {
  implicit val outlineFormat: RootJsonFormat[GameOutline] = jsonFormat1(GameOutline)
  implicit val resultFormat: RootJsonFormat[Result] = jsonFormat1(Result)
}
