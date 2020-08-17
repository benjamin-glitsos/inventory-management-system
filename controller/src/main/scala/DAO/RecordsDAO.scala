import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._

object RecordsDAO {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def upsert(record: RecordEdit) = {
        sql"""
        INSERT INTO records (uuid, created_at, created_by)
        VALUES (${record.uuid}, NOW(), ${record.user_id})
        ON CONFLICT (uuid)
        DO UPDATE SET
              edits = records.edits + 1
            , edited_at = EXCLUDED.created_at
            , edited_by = EXCLUDED.created_by
        RETURNING id, edited_by
        """.query[RecordReturn].unique
    }
}