-- THIS IS THE FILE FOR PROD SCHEMA.

-- Create the employee sequence and the table

CREATE SEQUENCE IF NOT EXISTS employee_seq;

CREATE TABLE IF NOT EXISTS employee(
	employee_id BIGINT NOT NULL DEFAULT nextval('employee_seq') PRIMARY KEY,
	email VARCHAR(100) NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL
);


-- Create the project sequence and table

CREATE SEQUENCE IF NOT EXISTS project_seq;

CREATE TABLE IF NOT EXISTS project(
	project_id BIGINT NOT NULL DEFAULT nextval('project_seq') PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	stage VARCHAR(100) NOT NULL,
	description VARCHAR(500) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL
);


-- Create the project employee relationship table

CREATE TABLE IF NOT EXISTS project_employee(
	project_id BIGINT REFERENCES project,
	employee_id BIGINT REFERENCES employee
);


-- Create the user accounts sequence and table

CREATE SEQUENCE IF NOT EXISTS user_accounts_seq;

CREATE TABLE IF NOT EXISTS user_accounts(
	user_id BIGINT NOT NULL DEFAULT nextval('user_accounts_seq') PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(255),
	enabled BOOLEAN NOT NULL
);

