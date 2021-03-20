CREATE FUNCTION update_for_users_with_meta(
    old_username text
  , new_username text
  , new_email_address text
  , new_first_name text
  , new_last_name text
  , new_other_names text
  , new_password text
  , new_notes text
)
RETURNS void
AS $$
BEGIN
    WITH users_update AS (
        UPDATE users
        SET username=new_username
        , email_address=new_email_address
        , first_name=new_first_name
        , last_name=new_last_name
        , other_names=new_other_names
        , password=sha1_encrypt(new_password)
        WHERE username=old_username
        RETURNING meta_id
    )
    UPDATE meta
    SET edited_at=NOW(), notes=new_notes, edits=edits + 1
    WHERE id=(SELECT meta_id FROM users_update);
END;
$$ LANGUAGE plpgsql;