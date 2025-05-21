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
    university VARCHAR(100) NOT NULL,
    faculty VARCHAR(100) NOT NULL,
    major VARCHAR(100) NOT NULL,
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
	gpa DECIMAL(3,2) NOT NULL,
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


--alter for news table
ALTER TABLE news DROP COLUMN content
-------------------------------------------------------------------------------------------------------------------------



- PASTRIMI I RRESHTAVE EKZISTUES
TRUNCATE TABLE applications, faculties, faq, feedback, majors, news, notification, review, scholarship_tags, scholarships, students, universities, users RESTART IDENTITY CASCADE;

- EKZEKUTONI GenerateTheSuperAdmin
- Kycuni ne username "knk25@admin.com" password "knk25"
- te menaxhimi i users, shtoni perdoruesit manualisht:
Emri: Bleron
Mbiemri: Baftiu
Email: adminbleron@admin.uni-pr.edu
Password: Knk_2025

Emri: Olsa
Mbiemri: Domi
Email: adminolsa@admin.uni-pr.edu
Password: Knk_2025

Emri: Olti
Mbiemri: Krasniqi
Email: adminolti@admin.uni-pr.edu
Password: Knk_2025

Emri: Riga
Mbiemri: Zubaku
Email: adminriga@admin.uni-pr.edu
Password: Knk_2025

Emri: Rreze
Mbiemri: Ejupi
Email: adminrreze@admin.uni-pr.edu
Password: Knk_2025

Emri: Valmir
Mbiemri: Mustafa
Email: adminvalmir@admin.uni-pr.edu
Password: Knk_2025


-----Shtimi i universiteteve, fakulteteve, drejtimeve, ME QUERIES
INSERT INTO universities (name, city, country) VALUES
('University of Prishtina', 'Prishtina', 'Kosovo'),
('University of Prizren', 'Prizren', 'Kosovo'),
('University of Gjakova', 'Gjakova', 'Kosovo');

INSERT INTO faculties (university_id, name) VALUES
(1, 'Fakulteti i Inxhinierise Elektrike dhe Kompjuterike'),
(1, 'Fakulteti Juridik');

INSERT INTO faculties (university_id, name) VALUES
(2, 'Fakulteti i Artit'),
(2, 'Faculty i Mjekesise');

INSERT INTO faculties (university_id, name) VALUES
(3, 'Faculty i Edukimit'),
(3, 'Faculty i Mjekesise');

INSERT INTO majors (faculty_id, name) VALUES
(1, 'Inxhinieri Kompjuterike'),
(1, 'Inxhinieri Elektrike');

INSERT INTO majors (faculty_id, name) VALUES
(2, 'Kriminalistike'),
(2, 'Marredhenie Nderkombetare');

INSERT INTO majors (faculty_id, name) VALUES
(3, 'Arti Figurativ'),
(3, 'Muzike');

INSERT INTO majors (faculty_id, name) VALUES
(4, 'Mjekesi e Pergjithshme'),
(4, 'Infermieri');

INSERT INTO majors (faculty_id, name) VALUES
(5, 'Edukimi i Hershem'),
(5, 'Mesimdhenie ne Matematike');

INSERT INTO majors (faculty_id, name) VALUES
(6, 'Stomatologji'),
(6, 'Mjekesi e pergjithshme');

----Tash MANUALISHT
- shkoni te signup:
Emri: Bleron
Mbiemri: Baftiu
email: bleronbaftiu@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF

Emri: Olsa
Mbiemri: Domi
email: olsadomi@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF

Emri: Olti
Mbiemri: Krasniqi
email: oltikrasniqi@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF

Emri: Riga
Mbiemri: Zubaku
email: rigazubaku@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF

Emri: Rreze
Mbiemri: Ejupi
email: rrezeejupi@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF

Emri: Valmir
Mbiemri: Mustafa
email: valmirmustafa@student.uni-pr.edu
password: Knk_2025
Universiteti: .....
Fakulteti: .....
Drejtimi: ......
Transkripta: upload PDF


------manualisht
kycuni si admin (knk25@admin.com) edhe te menaxhimi i perdoruesve, Validoni Studentat

-- me query
INSERT INTO scholarships (
    scholarship_name, provider, amount, deadline_date, required_gpa, required_year, required_major, status
) VALUES
(
    'Bursa për Ekselencë Akademike',
    'Ministria e Arsimit',
    1000,
    '2025-07-01',
    9.00,
    3,
    'Inxhinieri Kompjuterike',
    'active'
),
(
    'Bursa për Vajza në Teknologji',
    'TechGirls Foundation',
    1200,
    '2025-06-15',
    8.50,
    2,
    'Shkenca Kompjuterike',
    'active'
),
(
    'Bursa për Studentë',
    'UNICEF',
    800,
    '2025-08-30',
    NULL,
    NULL,
    NULL,
    'active'
),
(
    'Bursa për Studime Ndërkombëtare',
    'Erasmus+',
    1500,
    '2025-07-10',
    8.00,
    4,
    NULL,
    'active'
),
(
    'Bursa për Veteranë të Familjes',
    'Fondacioni Kombëtar',
    900,
    '2025-06-25',
    7.00,
    1,
    'Drejtësi',
    'active'
);


----MANUALISHT APLIKONI NEPER DISA BURSA

---- MANULISHT kycuni si admin edhe aprovoni/refuzoni aplikime t bursave

-- me query shtoni notifications
INSERT INTO notification (student_id, message, is_broadcast)
VALUES
(NULL, 'Afati i fundit për aplikim për bursat është data 30 qershor. Aplikoni sa më shpejt!', TRUE),

(NULL, 'Ju njoftojmë që dokumentet e nevojshme duhet të ngarkohen në format PDF.', TRUE),

(NULL, 'Platforma do të jetë në mirëmbajtje ditën e diel, ora 00:00 - 06:00.', TRUE),

(NULL, 'Përfituesit e bursave do të njoftohen me email deri më 15 korrik.', TRUE);


-----me query shtoni FAQ
INSERT INTO faq (question, answer)
VALUES
('Si të aplikoj për një bursë?',
 'Për të aplikuar për bursë, duhet të regjistroheni në platformë si student dhe të plotësoni formularin e aplikimit në seksionin “Apliko”.'),

('Çfarë dokumentesh nevojiten për aplikim?',
 'Nevojitet transkripta e notave.'),

('Si mund ta di nëse aplikimi im është aprovuar?',
 'Do të informoheni përmes një njoftimi në platformë.'),

('Çfarë ndodh nëse nuk plotësoj kriteret?',
 'Nëse nuk i plotësoni kriteret minimale për një bursë të caktuar, aplikimi juaj do refuzohet dhe per kete do njoftoheni.'),

('A mund të aplikoj për më shumë se një bursë?',
 'Po, ju mund të aplikoni për më shumë se një bursë, për aq kohë sa i plotësoni kriteret për secilën.');


-- me query shtoni feedback
INSERT INTO feedback (user_id, message)
VALUES
(13, 'Platforma është shumë e lehtë për t’u përdorur. Faleminderit për mundësinë për të aplikuar për bursa!'),

(14, 'Kam hasur një problem gjatë ngarkimit të dokumentit të transkriptës. Mund të kontrolloni funksionalitetin?'),

(16, 'A do të ketë më shumë bursa të reja të disponueshme gjatë vitit akademik?'),

(17, 'Shumë faleminderit për këtë platformë – është ndihmesë e madhe për ne studentët me nevoja ekonomike.');


-- me query shtoni disa pergjigje
UPDATE feedback
SET response = 'Faleminderit për komentin tuaj! Do te punojme vazhdimisht per permiresimin e funksionaliteteve te ofruara.'
WHERE id = 1;

UPDATE feedback
SET response = 'Na vjen keq per problemin e hasur. Do ta shqyrtojme shpejte!'
WHERE id = 2;

-- me query shtoni scholarship tags
INSERT INTO scholarship_tags (tag_name) VALUES
('STEM'),
('Humanities'),
('International'),
('GENERAL'),
('Merit-Based'),
('Women in Tech'),
('Research'),
('Erasmus');

-- MANUALISHT shtoni news










