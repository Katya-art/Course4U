-- Table: users
CREATE TABLE IF NOT EXISTS users (
                       id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       full_name VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
)
    ENGINE = InnoDB;

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
                       id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping user and role: user_roles
CREATE TABLE IF NOT EXISTS user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,

                            FOREIGN KEY (user_id) REFERENCES users (id),
                            FOREIGN KEY (role_id) REFERENCES roles (id),

                            UNIQUE (user_id, role_id)
)
    ENGINE = InnoDB;

-- Table: courses
CREATE TABLE IF NOT EXISTS courses (
                                     id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     theme VARCHAR(255) NOT NULL,
                                     duration VARCHAR(255) NOT NULL,
                                     number_of_students INT NOT NULL
)
    ENGINE = InnoDB;

-- Table: marks
CREATE TABLE IF NOT EXISTS marks (
                                     id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping course and teacher: course_teacher
CREATE TABLE IF NOT EXISTS course_teacher (
                                          course_id INT NOT NULL,
                                          teacher_id INT NOT NULL,

                                          FOREIGN KEY (course_id) REFERENCES courses (id),
                                          FOREIGN KEY (teacher_id) REFERENCES users (id),

                                          UNIQUE (course_id, teacher_id)
)
    ENGINE = InnoDB;

-- Table for mapping course, students and marks: course_students_marks
CREATE TABLE IF NOT EXISTS course_students_marks (
                                              course_id INT NOT NULL,
                                              student_id INT NOT NULL,
                                              mark_id INT NOT NULL ,

                                              FOREIGN KEY (course_id) REFERENCES courses (id),
                                              FOREIGN KEY (student_id) REFERENCES users (id),
                                              FOREIGN KEY (mark_id) REFERENCES marks (id),

                                              UNIQUE (course_id, student_id, mark_id)
)
    ENGINE = InnoDB;

-- Table: conditions
CREATE TABLE IF NOT EXISTS conditions (
                                     id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping course and condition: course_condition
CREATE TABLE IF NOT EXISTS course_conditions (
                                          course_id INT NOT NULL,
                                          condition_id INT NOT NULL,

                                          FOREIGN KEY (course_id) REFERENCES courses (id),
                                          FOREIGN KEY (condition_id) REFERENCES conditions (id),

                                          UNIQUE (course_id, condition_id)
)
    ENGINE = InnoDB;

-- Table: statuses
CREATE TABLE IF NOT EXISTS statuses (
                                          id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping student and condition: student_status
CREATE TABLE IF NOT EXISTS student_status (
                                                 student_id INT NOT NULL,
                                                 status_id INT NOT NULL,

                                                 FOREIGN KEY (student_id) REFERENCES users (id),
                                                 FOREIGN KEY (status_id) REFERENCES statuses (id),

                                                 UNIQUE (student_id, status_id)
)
    ENGINE = InnoDB;

-- Insert data

INSERT INTO users VALUES (1, 'Kateryna Kravchenko', 'KaterynaKravchenko', 'katakravchenko01@gmail.com',
                          '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles VALUES (1, 'ROLE_STUDENT');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO roles VALUE (3, 'ROLE_TEACHER');

INSERT INTO user_roles VALUES (1, 2);

INSERT INTO conditions VALUES (1, 'NOT_STARTED');
INSERT INTO conditions VALUES (2, 'IN_PROGRESS');
INSERT INTO conditions VALUES (3, 'COMPLETED');

INSERT INTO marks VALUES (1, 'A');
INSERT INTO marks VALUES (2, 'B');
INSERT INTO marks VALUES (3, 'C');
INSERT INTO marks VALUES (4, 'D');
INSERT INTO marks VALUES (5, 'E');

INSERT INTO statuses VALUES (1, 'UNLOCK');
INSERT INTO statuses VALUES (2, 'BLOCKED');