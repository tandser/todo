DELETE FROM notes;
DELETE FROM users;

ALTER SEQUENCE seq_users RESTART WITH 1;
ALTER SEQUENCE seq_notes RESTART WITH 1;

INSERT INTO users (name, email, password, role, created, updated) VALUES
  ('Lynn Douglas', 'l.douglas@gmail.com', '$2a$10$cqshpRXyPwKV19/p6hy8f.UD0eW08aLyjXEbeosxIXvC/pkov9hiS', 'ADMIN', now(), now()),
  ('Scott Welch',  's.welch@gmail.com',   '$2a$10$IJCpBm3bSRpufGZOKNuL1eC1rGDBSrOUSvjr2dADkRNaQBG63/ioS', 'USER',  now(), now());

INSERT INTO notes (text, user_id, created, updated) VALUES
  ('text 1 note', 1, now(), now()),
  ('text 2 note', 1, now(), now()),
  ('text 3 note', 1, now(), now()),

  ('text 1 note', 2, now(), now()),
  ('text 2 note', 2, now(), now()),
  ('text 3 note', 2, now(), now());