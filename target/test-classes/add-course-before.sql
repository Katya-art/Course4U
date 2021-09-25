DELETE FROM course_teacher;
DELETE FROM course_conditions;
DELETE FROM course_students_marks;
DELETE FROM courses;

INSERT INTO courses VALUES (1, 'Data Science Math Skills', 'Math and Logic', 4, 0);

INSERT INTO course_conditions VALUES (1, 1);

INSERT INTO course_teacher VALUES (1, 2);