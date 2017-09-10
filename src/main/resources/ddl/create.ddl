DROP TABLE    IF EXISTS notes;
DROP TABLE    IF EXISTS users;

DROP SEQUENCE IF EXISTS seq_notes;
DROP SEQUENCE IF EXISTS seq_users;

-------------------

CREATE SEQUENCE seq_users START 1;

CREATE TABLE users (
  id       INTEGER   PRIMARY KEY DEFAULT nextval('seq_users'),
  name     VARCHAR   NOT NULL,
  email    VARCHAR   NOT NULL,
  password VARCHAR   NOT NULL,
  role     VARCHAR   NOT NULL,
  disabled BOOLEAN   NOT NULL DEFAULT FALSE,
  created  TIMESTAMP NOT NULL DEFAULT now(),
  updated  TIMESTAMP,
  version  INTEGER   NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX unique_users ON users (email);

-------------------

CREATE SEQUENCE seq_notes START 1;

CREATE TABLE notes (
  id       INTEGER   PRIMARY KEY DEFAULT nextval('seq_notes'),
  text     VARCHAR   NOT NULL,
  done     BOOLEAN   NOT NULL DEFAULT FALSE,
  user_id  INTEGER   NOT NULL REFERENCES users ON DELETE CASCADE,
  created  TIMESTAMP NOT NULL DEFAULT now(),
  updated  TIMESTAMP,
  version  INTEGER   NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX unique_notes ON notes (user_id);