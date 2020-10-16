CREATE VIEW meta_list AS
    SELECT
        meta.id
      , meta.created_at
      , creator.username AS created_by
      , meta.opened_at
      , opener.username AS opened_by
      , meta.edited_at
      , editor.username AS edited_by
      , meta.deleted_at
    FROM meta
    LEFT JOIN users creator ON meta.created_by_id = creator.id
    LEFT JOIN users opener ON meta.opened_by_id = opener.id
    LEFT JOIN users editor ON meta.edited_by_id = editor.id;

CREATE VIEW meta_open AS
    SELECT
        meta.id
      , meta.created_at
      , creator.username AS created_by
      , meta.opens
      , meta.opened_at
      , opener.username AS opened_by
      , meta.edits
      , meta.edited_at
      , editor.username AS edited_by
      , meta.deleted_at
      , deletor.username AS deleted_by
      , meta.restored_at
      , restorer.username AS restored_by
      , meta.notes
    FROM meta
    LEFT JOIN users creator ON meta.created_by_id = creator.id
    LEFT JOIN users opener ON meta.opened_by_id = opener.id
    LEFT JOIN users editor ON meta.edited_by_id = editor.id
    LEFT JOIN users deletor ON meta.deleted_by_id = deletor.id
    LEFT JOIN users restorer ON meta.restored_by_id = restorer.id;

CREATE VIEW users_list AS
    SELECT
        u.email_address
      , u.username
      , u.password
      , m.created_at
      , m.created_by
      , m.opened_at
      , m.opened_by
      , m.edited_at
      , m.edited_by
      , m.deleted_at
    FROM users u
    LEFT JOIN meta_list m ON u.meta_id = m.id;

CREATE VIEW users_open AS
    SELECT
        u.email_address
      , u.username
      , u.password
      , m.created_at
      , m.created_by
      , m.opens
      , m.opened_at
      , m.opened_by
      , m.edits
      , m.edited_at
      , m.edited_by
      , m.deleted_at
      , m.deleted_by
      , m.restored_at
      , m.restored_by
      , m.notes
    FROM users u
    LEFT JOIN meta_open m ON u.meta_id = m.id;
