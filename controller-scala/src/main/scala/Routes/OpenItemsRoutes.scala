import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_import.general._

object OpenItemsRoutes {
  final def apply(key: String): Route = get {
    ValidationDirective("open-item") { body: ujson.Value =>
      complete(ItemsService.open(key))
    }
  }
}