-- Ensure the table is created if not existing. This can be handled by Hibernate, but here for clarity.
CREATE TABLE IF NOT EXISTS user_account (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Insert initial data, handling potential duplicates to avoid errors on app restart.
INSERT INTO user_account (username, password) VALUES ('user1', 'password1')
ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password);
INSERT INTO user_account (username, password) VALUES ('user2', 'password2')
ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password);