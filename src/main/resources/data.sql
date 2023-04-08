INSERT INTO roles (NAME)
SELECT 'ROLE_USER' FROM dual WHERE NOT EXISTS(SELECT 1 FROM roles WHERE NAME='ROLE_USER');

INSERT INTO roles (NAME)
SELECT 'ROLE_ADMIN' FROM dual WHERE NOT EXISTS(SELECT 1 FROM roles WHERE NAME='ROLE_ADMIN');

INSERT INTO roles (NAME)
SELECT 'ROLE_MODERATOR' FROM dual WHERE NOT EXISTS(SELECT 1 FROM roles WHERE NAME='ROLE_MODERATOR');