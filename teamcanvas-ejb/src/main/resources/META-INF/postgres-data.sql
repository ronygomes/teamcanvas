
INSERT INTO users (id, email, first_name, last_name, hashed_password)
VALUES (nextval('users_id_seq'), 'john@example.com', 'John', 'Doe', '1');

INSERT INTO users (id, email, first_name, last_name, hashed_password)
VALUES (nextval('users_id_seq'), 'jane@example.com', 'Jane', 'Doe', '1');
