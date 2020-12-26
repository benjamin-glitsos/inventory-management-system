import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import upickle_bundle.implicits._

trait UsersServicesList {
  def list(entityJson: String): String = {
    val body: ujson.Value = ujson.read(entityJson)
    val pageNumber: Int   = body("page_number").num.toInt
    val pageLength: Int   = body("page_length").num.toInt

    (for {
      totalItems <- UsersDAO.count().map(_.toInt)

      val offset: Int     = (pageNumber - 1) * pageLength
      val totalPages: Int = Math.ceil(totalItems.toFloat / pageLength).toInt
      val rangeStart: Int = 1 + offset
      val rangeEnd: Int   = rangeStart + pageLength - 1

      data <- UsersDAO.list(offset, pageLength)

      val output: String = write(
        ujson.Obj(
          "total_items" -> ujson.Num(totalItems),
          "total_pages" -> ujson.Num(totalPages),
          "range_start" -> ujson.Num(rangeStart),
          "range_end"   -> ujson.Num(rangeEnd),
          "data"        -> writeJs(data)
        )
      )
    } yield (output))
      .transact(xa)
      .unsafeRunSync
  }
}