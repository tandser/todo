DELETE FROM notes;
DELETE FROM users;

ALTER SEQUENCE seq_users RESTART WITH 1;
ALTER SEQUENCE seq_notes RESTART WITH 1;

INSERT INTO users (name, email, password, role) VALUES
  ('Lynn Douglas', 'l.douglas@gmail.com', '$2a$10$cqshpRXyPwKV19/p6hy8f.UD0eW08aLyjXEbeosxIXvC/pkov9hiS', 'ADMIN'),
  ('Scott Welch',  's.welch@gmail.com',   '$2a$10$IJCpBm3bSRpufGZOKNuL1eC1rGDBSrOUSvjr2dADkRNaQBG63/ioS', 'USER');

INSERT INTO notes (created, text, user_id) VALUES
  ('2017-09-10 10:00:00', 'text 1 notes', 1),
  ('2017-09-10 11:00:00', 'text 2 notes', 1),
  ('2017-09-10 12:00:00', 'text 3 notes', 1),

  ('2017-09-10 10:00:00', 'text 1 notes', 2),
  ('2017-09-10 11:00:00', 'text 2 notes', 2),
  ('2017-09-10 12:00:00', 'text 3 notes', 2);