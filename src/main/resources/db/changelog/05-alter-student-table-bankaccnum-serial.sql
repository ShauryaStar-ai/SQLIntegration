-- liquibase formatted sql

-- changeset shaurya:5
-- Create a sequence for the bankAccNum
CREATE SEQUENCE IF NOT EXISTS students_bankaccnum_seq;

-- Set the default value for the bankAccNum column to the next value from the sequence
ALTER TABLE students ALTER COLUMN bankaccnum SET DEFAULT nextval('students_bankaccnum_seq');

-- Associate the sequence with the column so it's dropped if the column is dropped
ALTER SEQUENCE students_bankaccnum_seq OWNED BY students.bankaccnum;

-- Update existing rows to have a unique bank account number
UPDATE students SET bankaccnum = nextval('students_bankaccnum_seq') WHERE bankaccnum IS NULL OR bankaccnum <= 0;

-- Make the column NOT NULL
ALTER TABLE students ALTER COLUMN bankaccnum SET NOT NULL;

-- rollback ALTER TABLE students ALTER COLUMN bankaccnum DROP NOT NULL;
-- rollback ALTER TABLE students ALTER COLUMN bankaccnum DROP DEFAULT;
-- rollback DROP SEQUENCE IF EXISTS students_bankaccnum_seq;

