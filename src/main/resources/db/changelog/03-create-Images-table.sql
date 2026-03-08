-- liquibase formatted sql

-- changeset shaurya:3
CREATE TABLE Images (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    data BYTEA NOT NULL,
    uploadDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



