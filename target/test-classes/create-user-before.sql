DELETE FROM student_status;
DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO users VALUES (1, 'Kateryna Kravchenko', 'KaterynaKravchenko', 'katakravchenko01@gmail.com',
                          '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG'),
                         (2, 'Ela Reader', 'ElaReader', 'ela.reader@gmail.com',
                          '$2a$10$oJ8fEB2g67GDfKnPe1rYXuVNPsq3gvqa3K8xr6MJ.uSlyFTjaZx6.'),
                         (3, 'Ayva Bowers', 'AyvaBowers', 'ayva.bowers@gmail.com',
                          '$2a$10$Y8mPwq8D.q95Q2ezzcDJreRR7n7YUiWSo62S/3pvDp0djcMnf75C.');

INSERT INTO user_roles VALUES (1, 2),
                              (2, 3),
                              (3, 1);

INSERT INTO student_status VALUES (3, 1);