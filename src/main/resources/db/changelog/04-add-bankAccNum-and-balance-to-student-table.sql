-- liquibase formatted sql

-- changeset shaurya:4
ALTER TABLE students ADD bankAccNum INT;
ALTER TABLE students ADD balance DOUBLE PRECISION;
