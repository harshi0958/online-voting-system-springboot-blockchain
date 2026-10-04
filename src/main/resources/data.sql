/*create database myvoting;*/
USE myvoting;

-- Insert Candidates
INSERT INTO candidate (name, vote_count) VALUES ('Alice Johnson', 0);
INSERT INTO candidate (name, vote_count) VALUES ('Bob Smith', 0);
INSERT INTO candidate (name, vote_count) VALUES ('Charlie Lee', 0);

-- Insert Voters
INSERT INTO voter (name, email, has_voted) VALUES ('John Doe', 'john@example.com', false);
INSERT INTO voter (name, email, has_voted) VALUES ('Jane Roe', 'jane@example.com', false);
INSERT INTO voter (name, email, has_voted) VALUES ('Max Payne', 'max@example.com', false);


drop database myvoting;

SHOW DATABASES;

SHOW TABLES;

INSERT INTO voter (name, email, password, has_voted)
VALUES ('Rahul', 'rahul@gmail.com', '12345', false);

SELECT * FROM voter;

UPDATE voter SET has_voted = true WHERE id = 1;

SELECT * FROM voter;

INSERT INTO candidate (name, vote_count)
VALUES ('Candidate A', 0);
INSERT INTO candidate (name) VALUES ('Candidate B');
SELECT * FROM candidate;
SELECT * FROM candidate WHERE id = 1;


SELECT id, name, vote_count FROM candidate;
SELECT id, has_voted FROM voter;

CREATE TABLE voter (
  aadhaar_no VARCHAR(12) PRIMARY KEY,
  name VARCHAR(50),
  mobile VARCHAR(15),
  has_voted BOOLEAN
);


ALTER TABLE voter
ADD COLUMN aadhaar_no VARCHAR(12) UNIQUE;

ALTER TABLE voter
ADD COLUMN aadhaar_no VARCHAR(12) NOT NULL;
ALTER TABLE voter
ADD CONSTRAINT uq_aadhaar UNIQUE (aadhaar_no);

DESC voter;

SHOW INDEX FROM voter;


UPDATE voter
SET aadhaar_no = '123456789012'
WHERE id = 4;

SELECT id, aadhaar_no, password, has_voted FROM voter;

DELETE FROM voter WHERE aadhaar_no IS NULL;

ALTER TABLE voter
ADD CONSTRAINT uq_aadhaar UNIQUE (aadhaar_no);


DROP TABLE admin;

CREATE TABLE admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

INSERT INTO admin (username, password)
VALUES ('admin', 'admin123');

DESC candidate;
INSERT INTO candidate (name, vote_count)
VALUES
('Candidate A', 2),
('Candidate B', 0),
('Candidate C', 0);

SELECT * FROM candidate;
DELETE FROM candidate;
ALTER TABLE candidate AUTO_INCREMENT = 1;

INSERT INTO candidate (name, vote_count) VALUES
('Candidate A', 0),
('Candidate B', 0),
('Candidate C', 0);

TRUNCATE TABLE candidate;

INSERT INTO candidate (name, vote_count) VALUES
('Candidate A', 0),
('Candidate B', 0),
('Candidate C', 0);

SELECT * FROM candidate;
SELECT * FROM admin;










