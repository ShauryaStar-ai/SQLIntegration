-- liquibase formatted sql

-- changeset shaurya:2

CREATE TABLE Books (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ownerId INT NOT NULL REFERENCES Students(id)
);
