CREATE VIEW items_list AS
    SELECT
        key
      , name
      , truncate(50, description) AS description
      , created_at
      , edited_at
    FROM items_with_meta
    WHERE is_deleted = false;
