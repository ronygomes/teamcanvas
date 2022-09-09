CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE teams_id_seq;
CREATE SEQUENCE projects_id_seq;
CREATE SEQUENCE phases_id_seq;
CREATE SEQUENCE tasks_id_seq;

-- Note: Postgres produces error for keyword `user` as table name
CREATE TABLE users (
    id INTEGER DEFAULT nextval('users_id_seq') PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(70) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    hashed_password VARCHAR(50) NOT NULL,
    profile_image bytea NULL
);

CREATE TABLE teams (
    id INTEGER DEFAULT nextval('teams_id_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    creator_id INTEGER NOT NULL,
    CONSTRAINT fk_team_creator_id FOREIGN KEY(creator_id) REFERENCES users(id)
);

CREATE TABLE team_members (
    team_id INTEGER NOT NULL,
    member_id INTEGER NOT NULL,
    PRIMARY KEY (team_id, member_id),
    CONSTRAINT fk_team_member_team_id FOREIGN KEY(team_id) REFERENCES teams(id),
    CONSTRAINT fk_team_member_member_id FOREIGN KEY(member_id) REFERENCES users(id)
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
    CONSTRAINT fk_project_creator_id FOREIGN KEY(creator_id) REFERENCES users(id)
);

CREATE TABLE phases (
    id INTEGER DEFAULT nextval('phases_id_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    ordinal INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    CONSTRAINT fk_phase_project_id FOREIGN KEY(project_id) REFERENCES projects(id)
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
    CONSTRAINT fk_task_creator_id FOREIGN KEY(creator_id) REFERENCES users(id),
    CONSTRAINT fk_task_phase_id FOREIGN KEY(phase_id) REFERENCES phases(id)
);

