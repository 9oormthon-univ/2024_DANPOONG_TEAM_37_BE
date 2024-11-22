-- http://localhost:8080/h2-console
CREATE TABLE field (
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE mode (
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE position (
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE skill (
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO field (name) VALUES ('PROJECT');
INSERT INTO field (name) VALUES ('STUDY');

INSERT INTO mode (name) VALUES ('ONLINE');
INSERT INTO mode (name) VALUES ('OFFLINE');

INSERT INTO position (name) VALUES ('FRONTEND');
INSERT INTO position (name) VALUES ('BACKEND');

INSERT INTO skill (name) VALUES ('SPRING');
INSERT INTO skill (name) VALUES ('JAVA');
INSERT INTO skill (name) VALUES ('MYSQL');