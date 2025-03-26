CREATE TABLE "city" (
  "id" serial PRIMARY KEY,
  "name" varchar NOT NULL,
  "postal_code" varchar NOT NULL
);

CREATE TABLE "user_type" (
  "id" serial PRIMARY KEY,
  "type_name" varchar UNIQUE NOT NULL
);

CREATE TABLE "user" (
  "id" serial PRIMARY KEY,
  "first_name" varchar NOT NULL,
  "last_name" varchar NOT NULL,
  "email" email UNIQUE NOT NULL,
  "password" varchar NOT NULL,
  "phone" varchar NOT NULL,
  "user_type_id" int NOT NULL,
  "city_id" int NOT NULL
);

CREATE TABLE "grade_level" (
  "id" serial PRIMARY KEY,
  "year" varchar NOT NULL
);

CREATE TABLE "class" (
  "id" serial PRIMARY KEY,
  "name" varchar NOT NULL,
  "description" varchar,
  "grade_level_id" int NOT NULL
);

CREATE TABLE "student_class" (
  "student_id" int NOT NULL,
  "class_id" int NOT NULL
);

CREATE TABLE "subject" (
  "id" serial PRIMARY KEY,
  "name" varchar NOT NULL,
  "description" varchar
);

CREATE TABLE "teacher_subject" (
  "teacher_id" int NOT NULL,
  "subject_id" int NOT NULL
);

CREATE TABLE "student_subject" (
  "student_id" int NOT NULL,
  "subject_id" int NOT NULL
);

CREATE TABLE "grade" (
  "id" serial PRIMARY KEY,
  "student_id" int NOT NULL,
  "subject_id" int NOT NULL,
  "score" decimal NOT NULL,
  "date" date NOT NULL,
  "comment" varchar
);

CREATE TABLE "parent_student" (
  "parent_id" int NOT NULL,
  "student_id" int NOT NULL
);

ALTER TABLE "user" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id");

ALTER TABLE "user" ADD FOREIGN KEY ("user_type_id") REFERENCES "user_type" ("id");

ALTER TABLE "class" ADD FOREIGN KEY ("grade_level_id") REFERENCES "grade_level" ("id");

ALTER TABLE "student_class" ADD FOREIGN KEY ("class_id") REFERENCES "class" ("id");

ALTER TABLE "student_class" ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");

ALTER TABLE "teacher_subject" ADD FOREIGN KEY ("teacher_id") REFERENCES "user" ("id");

ALTER TABLE "teacher_subject" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");

ALTER TABLE "student_subject" ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");

ALTER TABLE "student_subject" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");

ALTER TABLE "grade" ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");

ALTER TABLE "grade" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");

ALTER TABLE "parent_student" ADD FOREIGN KEY ("parent_id") REFERENCES "user" ("id");

ALTER TABLE "parent_student" ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");
