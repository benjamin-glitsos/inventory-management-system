import doobie_bundle.database.dc._

trait ItemsOpenDAO {
  final def open(key: String) = {
    run(
      quote(
        query[ItemsWithMeta].filter(_.key == lift(key))
      )
    ).map(_.head)
  }

  final def incrementOpens(key: String) = {
    run(
      quote(
        query[ItemsWithMeta]
          .filter(_.key == lift(key))
          .update(x => x.opens -> (x.opens + 1))
      )
    )
  }
}