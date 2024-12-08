-- 데이터베이스 생성
CREATE DATABASE UniversityDB;

-- 생성한 데이터베이스 사용
USE UniversityDB;

-- 학생 테이블 생성
CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    s_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    gender CHAR(1) NOT NULL
);

-- 교수 테이블 생성
CREATE TABLE Professor (
    professor_id INT PRIMARY KEY,
    p_name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL
);

-- 동아리 테이블 생성
CREATE TABLE Club (
    club_id INT PRIMARY KEY,
    c_name VARCHAR(50) NOT NULL UNIQUE,
    creation_date DATE NOT NULL,
    location VARCHAR(100) NOT NULL
);

-- 활동 테이블 생성
CREATE TABLE Activity (
    activity_id INT PRIMARY KEY,
    a_name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE
);

-- 학생 활동 테이블 생성
CREATE TABLE Student_Activity (
    student_id INT,
    activity_id INT,
    role VARCHAR(50),
    hours INT,
    PRIMARY KEY (student_id, activity_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES Activity(activity_id) ON DELETE CASCADE
);

-- 학생 동아리 테이블 생성
CREATE TABLE Student_Club (
    student_id INT,
    club_id INT,
    PRIMARY KEY (student_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (club_id) REFERENCES Club(club_id) ON DELETE CASCADE
);

-- 동아리 회장 테이블 생성
CREATE TABLE Club_president (
    student_id INT,
    club_id INT,
    PRIMARY KEY (club_id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE RESTRICT,
    FOREIGN KEY (club_id) REFERENCES Club(club_id)
);

-- 활동 대표 테이블 생성
CREATE TABLE Activity_president (
    activity_id INT,
    student_id INT,
    PRIMARY KEY (activity_id),
    FOREIGN KEY (activity_id) REFERENCES Activity(activity_id) ON DELETE RESTRICT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id)
);

-- 교수 동아리 테이블 생성
CREATE TABLE Professor_Club (
    professor_id INT,
    club_id INT,
    PRIMARY KEY (professor_id, club_id),
    FOREIGN KEY (professor_id) REFERENCES Professor(professor_id) ON DELETE CASCADE,
    FOREIGN KEY (club_id) REFERENCES Club(club_id) ON DELETE CASCADE
);
