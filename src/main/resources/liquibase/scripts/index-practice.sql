-- liquibase formatted sql

-- changeset dnigrab:1
CREATE INDEX student_name_index on student (name);

-- changeset dnigrab:2
CREATE INDEX faculty_name_index on faculty (name);
CREATE INDEX faculty_color_index on faculty (color);
