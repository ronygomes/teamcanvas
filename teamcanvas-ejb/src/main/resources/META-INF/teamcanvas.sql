-- TODO
-- This file is not tested yet

CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE tasks_id_seq;
CREATE SEQUENCE phases_id_seq;
CREATE SEQUENCE projects_id_seq;

-- Note: Postgres produces error for keyword `user` as table name
CREATE TABLE users (
    id INTEGER DEFAULT nextval('users_id_seq') PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(70) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    hashed_password VARCHAR(50) NOT NULL,
    profile_image bytea NULL
);

CREATE TABLE projects (
    id INTEGER DEFAULT nextval('projects_id_seq') PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    status INTEGER,
    due_date TIMESTAMP,
    complete_percentage INTEGER,
    creation_date TIMESTAMP NOT NULL,
    creator_id INTEGER NOT NULL,
    last_modification_date TIMESTAMP,
    CONSTRAINT fk_creator_id FOREIGN KEY(creator_id) REFERENCES users(id)
);

CREATE TABLE phases (
    id INTEGER DEFAULT nextval('phases_id_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    ordinal INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    CONSTRAINT fk_project_id FOREIGN KEY(project_id) REFERENCES projects(id)
);

CREATE TABLE tasks (
    id INTEGER DEFAULT nextval('tasks_id_seq') PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    complete_percentage INTEGER NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    due_date TIMESTAMP,
    is_private boolean,
    last_modification_date TIMESTAMP,
    priority INTEGER,
    status INTEGER,
    creator_id INTEGER NOT NULL,
    phase_id INTEGER,
    CONSTRAINT fk_creator_id FOREIGN KEY(creator_id) REFERENCES users(id),
    CONSTRAINT fk_phase_id FOREIGN KEY(phase_id) REFERENCES phases(id)
);

INSERT INTO users (id, email, first_name, last_name, hashed_password)
VALUES (nextval('users_id_seq'), 'john@example.com', 'John', 'Doe', '1');
