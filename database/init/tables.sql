CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , created_at TIMESTAMP NOT NULL DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY
  , meta_id SMALLINT UNIQUE NOT NULL
  , email_address EMAIL_ADDRESS NOT NULL
  , username VARCHAR(20) UNIQUE NOT NULL
  , password VARCHAR(20) NOT NULL
);
