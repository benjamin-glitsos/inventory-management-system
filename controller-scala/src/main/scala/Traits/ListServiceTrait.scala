import Math.ceil
import doobie.implicits._
import doobie_bundle.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_bundle.general._

trait ListServiceTrait {
  final def calculatePageCount(pageLength: Int, items: Int): Int =
    ceil(items.toFloat / pageLength).toInt

  final def calculateOffset(pageNumber: Int, pageLength: Int): Int =
    (pageNumber - 1) * pageLength

  final def createListOutput[A](
      totalItemsCount: Int,
      filteredItemsCount: Int,
      pageItemsStart: Int,
      pageItemsEnd: Int,
      pageNumber: Int,
      pageLength: Int,
      items: List[A]
  ): String = {
    val totalPagesCount: Int = calculatePageCount(
      pageLength,
      totalItemsCount
    )

    val filteredPagesCount: Int = calculatePageCount(
      pageLength,
      filteredItemsCount
    )

    val pageItemsCount: Int = items.length

    write(
      ujson.Obj(
        "data" -> ujson.Obj(
          "total_items_count"    -> ujson.Num(totalItemsCount),
          "total_pages_count"    -> ujson.Num(totalPagesCount),
          "filtered_items_count" -> ujson.Num(filteredItemsCount),
          "filtered_pages_count" -> ujson.Num(filteredPagesCount),
          "page_items_count"     -> ujson.Num(pageItemsCount),
          "page_items_start"     -> ujson.Num(pageItemsStart),
          "page_items_end"       -> ujson.Num(pageItemsEnd),
          "page_number"          -> ujson.Num(pageNumber),
          "page_length"          -> ujson.Num(pageLength),
          "items"                -> writeJs[List[A]](items)
        )
      )
    )
  }
}
