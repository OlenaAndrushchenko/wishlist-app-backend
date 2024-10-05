-- Ensure the table is created if not existing. This can be handled by Hibernate, but here for clarity.
CREATE TABLE IF NOT EXISTS user_account (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_identifier VARCHAR(255) NOT NULL UNIQUE
);

-- Insert initial data, handling potential duplicates to avoid errors on app restart.
-- This statement will attempt to insert a new user only if a user with the same username does not already exist.
INSERT INTO user_account (username, password, email, user_identifier)
SELECT * FROM (SELECT 'user1' AS username, '$2a$12$goV6ZPdKrYzKTBXU02r.9.qUS.aSbtw.kGLK8S7z4gkF/4ygGu076' AS password, 'user1@email.com' AS email, 'uid12345' AS user_identifier) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM user_account WHERE username = tmp.username
);
