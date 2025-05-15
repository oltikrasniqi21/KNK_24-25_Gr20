ALTER TABLE applications
RENAME COLUMN application_id TO id;

ALTER TABLE faq
RENAME COLUMN faq_id TO id;

ALTER TABLE feedback
RENAME COLUMN feedback_id TO id;

ALTER TABLE news
RENAME COLUMN news_id TO id;

ALTER TABLE notification
RENAME COLUMN notification_id TO id;

ALTER TABLE review
RENAME COLUMN review_id TO id;

ALTER TABLE scholarships
RENAME COLUMN scholarship_id TO id;

ALTER TABLE students
RENAME COLUMN student_id TO id;

ALTER TABLE users
RENAME COLUMN user_id TO id;


CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	role VARCHAR(10) NOT NULL CHECK (LOWER(role) IN ('student', 'admin'))
);

CREATE TABLE students(
	student_id INTEGER PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
	gpa NUMERIC(3,2) NOT NULL CHECK(gpa BETWEEN 6.00 AND 10.00),
	year_of_study INTEGER NOT NULL CHECK (year_of_study BETWEEN 1 AND 5),
	university VARCHAR(50) NOT NULL,
	faculty VARCHAR(50) NOT NULL,
	major VARCHAR(50) NOT NULL,
	courses_left INTEGER NOT NULL CHECK (courses_left >= 0),
	priority VARCHAR(100) CHECK(priority IS NULL OR LOWER(priority) IN('veteran', 'disabled'))
);


CREATE TABLE scholarships(
	scholarship_id SERIAL PRIMARY KEY,
	scholarship_name VARCHAR(100) NOT NULL,
	provider VARCHAR(50) NOT NULL,
	amount INTEGER NOT NULL CHECK (amount >0),
	deadline_date DATE NOT NULL,
	required_gpa NUMERIC(3,2) CHECK (required_gpa IS NULL OR (required_gpa BETWEEN 6.00 AND 10.00)),
	required_year INTEGER CHECK (required_year IS NULL OR (required_year BETWEEN 1 AND 5)),
	required_major VARCHAR(50)
);
--
--CREATE TABLE review(
--    review_id SERIAL PRIMARY KEY,
--    application_id INTEGER NOT NULL,
--    admin_id INTEGER NOT NULL,
--    review_notes VARCHAR(100),
--    review_date DATE NOT NULL DEFAULT CURRENT_DATE,
--
--	FOREIGN KEY(application_id) REFERENCES applications(application_id) ON DELETE CASCADE,
--	FOREIGN KEY(admin_id) REFERENCES users(user_id) ON DELETE CASCADE
--);


CREATE TABLE applications(
	application_id SERIAL PRIMARY KEY,
	student_id INTEGER NOT NULL REFERENCES students(student_id) ON DELETE CASCADE,
	scholarship_id INTEGER NOT NULL REFERENCES scholarships(scholarship_id) ON DELETE CASCADE,
	application_date DATE NOT NULL DEFAULT CURRENT_DATE,
	status VARCHAR(10) NOT NULL CHECK (LOWER(status) IN ('pending', 'approved', 'rejected'))
);

#fillojme me shtimin e tabelave te reja
#feedback, faq, notification

CREATE TABLE feedback(
    feedback_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response TEXT
);

CREATE TABLE faq (
    faq_id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL
);

CREATE TABLE notification (
    notification_id SERIAL PRIMARY KEY,
    student_id INT REFERENCES Students(student_id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_status BOOLEAN DEFAULT FALSE
);

CREATE TABLE News (
  news_id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  content TEXT NOT NULL,
  scholarship_id INT REFERENCES Scholarships(scholarship_id) ON DELETE SET NULL,
  posted_by INT REFERENCES Users(user_id),
  posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  visible_until DATE
);


//query per shtimin e admin-users

INSERT INTO users (password, first_name, last_name, email, role)
VALUES ('12345678', 'Admin', 'Pr', 'administrata@admin.uni-pr.com', 'admin');

INSERT INTO users (password, first_name, last_name, email, role)
VALUES ('12345678', 'Admin', 'Pz', 'administrata@admin.uni-pz.com', 'admin');

INSERT INTO users (password, first_name, last_name, email, role)
VALUES ('12345678', 'Admin', 'Gjk', 'administrata@admin.uni-gjk.com', 'admin');

INSERT INTO users (password, first_name, last_name, email, role)
VALUES ('12345678', 'Admin', 'Fz', 'administrata@admin.uni-fz.com', 'admin');



//modifikim i tabeles Notification, qe me mujt mi kriju admin-i nje notification per generalStudents,
//e jo me logjiken 1to1, nje notification per nje student specifik...
//dmth studentId mbetet NULL

ALTER TABLE Notification ADD COLUMN is_broadcast BOOLEAN DEFAULT false;

INSERT INTO Notification (message, created_at, read_status, is_broadcast)
VALUES ('Welcome students!', CURRENT_TIMESTAMP, false, true);




//shtimi i nje studenti per testim
INSERT INTO users (password, first_name, last_name, email, role)
VALUES ('12345678', 'filan', 'fisteku', 'stdtest@student.uni-pr.com', 'student');

ALTER TABLE students ADD COLUMN proof_document TEXT;

ALTER TABLE users
ADD COLUMN status VARCHAR(20) DEFAULT 'pending';
