import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._
import upickle.default._
import CustomMethodDirectives.report
import scala.util.{Try}

object ListItemsRoutes {
  final def apply(): Route = report {
    Validation("list-items") { body: ujson.Value =>
      {
        val pageNumber: Int        = body("page_number").num.toInt
        val pageLength: Int        = body("page_length").num.toInt
        val search: Option[String] = Try(body("search").str).toOption
        val sort: (String, String) = read[(String, String)](body("sort"))

        complete(ItemsService.list(pageNumber, pageLength, search, sort))
      }
    }
  }
}