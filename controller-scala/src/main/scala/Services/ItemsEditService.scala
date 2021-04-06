import doobie.implicits._
import doobie_bundle.connection._

trait ItemsEditService {
  final def edit(
      oldKey: String,
      newKey: Option[String],
      name: Option[String],
      description: Option[String],
      notes: Option[String]
  ): String = {
    try {
      ItemsDAO
        .edit(
          oldKey,
          newKey,
          name,
          description,
          notes
        )
        .transact(xa)
        .unsafeRunSync
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
