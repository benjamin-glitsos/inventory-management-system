import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait UsersEditService
    extends ServiceTrait
    with DoobieConnectionTrait
    with UpickleTrait {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[Option[String]],
      emailAddress: Option[String],
      password: Option[String],
      additionalNotes: Option[Option[String]]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- UsersDAO
            .edit(
              oldUsername,
              newUsername,
              firstName,
              lastName,
              otherNames,
              emailAddress,
              password,
              additionalNotes
            )

          data <- UsersDAO.open(newUsername.getOrElse(oldUsername))

          val output: String = createDataOutput(writeJs(data))

        } yield (output))
          .transact(transactor)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
