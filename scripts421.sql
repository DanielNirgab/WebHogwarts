ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT name_unique UNIQUE (name),
    ADD CONSTRAINT age_constraint CHECK (age > 16);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (color, name);