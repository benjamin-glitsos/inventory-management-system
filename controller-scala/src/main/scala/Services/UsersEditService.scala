import doobie.implicits._
import doobie_bundle.connection._

trait UsersEditService {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      emailAddress: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[String],
      password: Option[String],
      notes: Option[String]
  ): String = {
    try {
      UsersDAO
        .edit(
          oldUsername,
          newUsername,
          firstName,
          lastName,
          otherNames,
          emailAddress,
          password,
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