-- Ensure the table is created if not existing. This can be handled by Hibernate, but here for clarity.
CREATE TABLE IF NOT EXISTS user_account (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_identifier VARCHAR(255) NOT NULL UNIQUE
);
-- -- Insert initial data, handling potential duplicates to avoid errors on app restart.
INSERT INTO user_account (username, password, email, user_identifier) VALUES ('user1', '$2a$12$goV6ZPdKrYzKTBXU02r.9.qUS.aSbtw.kGLK8S7z4gkF/4ygGu076', 'user1@email.com', 'uid12345')
ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password), email = VALUES(email), user_identifier = VALUES(user_identifier);
