-- all the queries to create the database Projekti-KNK

CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	role VARCHAR(10) NOT NULL CHECK (LOWER(role) IN ('student', 'admin')),
	status VARCHAR(20) DEFAULT 'pending'
);

CREATE TABLE students (
    id INTEGER PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    gpa NUMERIC(3,2) CHECK(gpa IS NULL OR gpa BETWEEN 6.00 AND 10.00),
    year_of_study INTEGER NOT NULL CHECK (year_of_study BETWEEN 1 AND 5),
    university VARCHAR(50) NOT NULL,
    faculty VARCHAR(50) NOT NULL,
    major VARCHAR(50) NOT NULL,
    priority VARCHAR(100) CHECK(priority IS NULL OR LOWER(priority) IN ('veteran', 'disabled')),
    document_path TEXT
);

CREATE TABLE scholarships(
	id SERIAL PRIMARY KEY,
	scholarship_name VARCHAR(100) NOT NULL,
	provider VARCHAR(50) NOT NULL,
	amount INTEGER NOT NULL CHECK (amount >0),
	deadline_date DATE NOT NULL,
	required_gpa NUMERIC(3,2) CHECK (required_gpa IS NULL OR (required_gpa BETWEEN 6.00 AND 10.00)),
	required_year INTEGER CHECK (required_year IS NULL OR (required_year BETWEEN 1 AND 5)),
	required_major VARCHAR(50),
	status VARCHAR(20)
);

CREATE TABLE applications (
	id SERIAL PRIMARY KEY,
	student_id INTEGER NOT NULL REFERENCES students(id) ON DELETE CASCADE,
	scholarship_id INTEGER NOT NULL REFERENCES scholarships(id) ON DELETE CASCADE,
	application_date DATE NOT NULL DEFAULT CURRENT_DATE,
	gpa INTEGER NOT NULL,
	transcript_path TEXT,
	status VARCHAR(10) NOT NULL DEFAULT 'pending' CHECK (LOWER(status) IN ('pending', 'approved', 'rejected'))
);

CREATE TABLE review(
    id SERIAL PRIMARY KEY,
    application_id INTEGER NOT NULL,
    admin_id INTEGER NOT NULL,
    review_notes VARCHAR(100),
    review_date DATE NOT NULL DEFAULT CURRENT_DATE,

	FOREIGN KEY(application_id) REFERENCES applications(id) ON DELETE CASCADE,
	FOREIGN KEY(admin_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE feedback(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response TEXT
);

CREATE TABLE faq (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL
);

CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_status BOOLEAN DEFAULT FALSE,
    is_broadcast BOOLEAN DEFAULT FALSE
);

CREATE TABLE scholarship_tags (
  id SERIAL PRIMARY KEY,
  tag_name TEXT UNIQUE NOT NULL
);

CREATE TABLE News (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  summary TEXT NOT NULL,
  content TEXT NOT NULL,
  scholarship_tag_id INT REFERENCES scholarship_tags(id) ON DELETE SET NULL,
  posted_by INT REFERENCES Users(id),
  posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  imagepath TEXT NOT NULL
);

CREATE TABLE Universities (
  id SERIAL PRIMARY KEY,
  name TEXT UNIQUE NOT NULL,
  city TEXT NOT NULL,
  country TEXT NOT NULL
);

CREATE TABLE faculties (
  id SERIAL PRIMARY KEY,
  university_id INT NOT NULL REFERENCES universities(id) ON DELETE CASCADE,
  name TEXT NOT NULL
);

CREATE TABLE majors (
  id SERIAL PRIMARY KEY,
  faculty_id INT NOT NULL REFERENCES faculties(id) ON DELETE CASCADE,
  name TEXT NOT NULL
);




--query to drop all tables in database
DO $$ DECLARE
    r RECORD;
BEGIN

    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS public.' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;


//boni qit query per modifikim t kolonave te scholarships
ALTER TABLE scholarships
ADD COLUMN status VARCHAR(20);



//kolona
