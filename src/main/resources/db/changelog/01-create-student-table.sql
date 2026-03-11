-- liquibase formatted sql

-- changeset shaurya:1
CREATE TABLE Students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    emailAddress VARCHAR(255) NOT NULL
);

