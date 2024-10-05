USE socialdata;

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS post_tags;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS user_settings;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    content BLOB,
    time_send TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);


CREATE TABLE posts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    body BLOB NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE follows (
    following_user_id INT NOT NULL,
    followed_user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (following_user_id) REFERENCES users(id),
    FOREIGN KEY (followed_user_id) REFERENCES users(id)
);


CREATE TABLE comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    body BLOB NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE votes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT,
    comment_id INT,
    vote_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);


CREATE TABLE tags (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE post_tags (
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);


CREATE TABLE notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message VARCHAR(255),
    read_status BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE user_settings (
    user_id INT NOT NULL,
    setting_key VARCHAR(255),
    setting_value VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE reports (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT,
    comment_id INT,
    reason TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);

INSERT INTO users (username, email, password_hash, role, created_at) VALUES
('user1', 'user1@example.com', 'hashed_password1', 'user', NOW()),
('user2', 'user2@example.com', 'hashed_password2', 'user', NOW()),
('admin1', 'admin1@example.com', 'hashed_password3', 'admin', NOW()),
('user3', 'user3@example.com', 'hashed_password4', 'user', NOW()),
('user4', 'user4@example.com', 'hashed_password5', 'user', NOW()),
('user5', 'user5@example.com', 'hashed_password6', 'user', NOW()),
('user6', 'user6@example.com', 'hashed_password7', 'user', NOW()),
('user7', 'user7@example.com', 'hashed_password8', 'user', NOW()),
('user8', 'user8@example.com', 'hashed_password9', 'user', NOW()),
('admin2', 'admin2@example.com', 'hashed_password10', 'admin', NOW());

INSERT INTO posts (title, body, user_id, status, created_at) VALUES
('Post Title 1', 'Content of post 1', 1, 'published', NOW()),
('Post Title 2', 'Content of post 2', 2, 'draft', NOW()),
('Post Title 3', 'Content of post 3', 1, 'published', NOW()),
('Post Title 4', 'Content of post 4', 3, 'published', NOW()),
('Post Title 5', 'Content of post 5', 2, 'draft', NOW()),
('Post Title 6', 'Content of post 6', 4, 'published', NOW()),
('Post Title 7', 'Content of post 7', 5, 'draft', NOW()),
('Post Title 8', 'Content of post 8', 6, 'published', NOW()),
('Post Title 9', 'Content of post 9', 7, 'published', NOW()),
('Post Title 10', 'Content of post 10', 8, 'draft', NOW());

INSERT INTO messages (sender_id, receiver_id, content, time_send) VALUES
(1, 2, 'Hello User 2!', NOW()),
(2, 1, 'Hi User 1!', NOW()),
(1, 3, 'Hello Admin!', NOW()),
(2, 4, 'How are you?', NOW()),
(3, 1, 'Welcome to the platform!', NOW()),
(4, 5, 'Hey User 5, how are you?', NOW()),
(5, 6, 'Did you see the latest update?', NOW()),
(6, 7, 'Looking forward to our meeting!', NOW()),
(7, 8, 'Great to connect with you!', NOW()),
(8, 1, 'Welcome to the platform, User 8!', NOW());


INSERT INTO follows (following_user_id, followed_user_id, created_at) VALUES
(1, 2, NOW()),
(2, 3, NOW()),
(1, 3, NOW()),
(3, 4, NOW()),
(4, 1, NOW()),
(5, 6, NOW()),
(6, 7, NOW()),
(7, 8, NOW()),
(8, 5, NOW()),
(4, 1, NOW());

INSERT INTO comments (post_id, user_id, body, created_at) VALUES
(1, 1, 'Great post!', NOW()),
(1, 2, 'Thanks for sharing!', NOW()),
(2, 3, 'Interesting content.', NOW()),
(3, 1, 'I learned a lot from this.', NOW()),
(4, 2, 'Can you elaborate more?', NOW()),
(6, 4, 'Nice post!', NOW()),
(6, 5, 'Very informative!', NOW()),
(7, 6, 'I disagree with some points.', NOW()),
(8, 7, 'Looking forward to more posts!', NOW()),
(9, 8, 'Can you provide more examples?', NOW());

INSERT INTO votes (user_id, post_id, comment_id, vote_type, created_at) VALUES
(1, 1, NULL, 'upvote', NOW()),
(2, 1, NULL, 'downvote', NOW()),
(3, 2, NULL, 'upvote', NOW()),
(4, NULL, 1, 'upvote', NOW()),
(1, NULL, 2, 'downvote', NOW()),
(5, 6, NULL, 'upvote', NOW()),
(6, 7, NULL, 'upvote', NOW()),
(7, 8, NULL, 'downvote', NOW()),
(8, NULL, 3, 'upvote', NOW()),
(4, NULL, 4, 'downvote', NOW());


INSERT INTO tags (name, created_at) VALUES
('Tag1', NOW()),
('Tag2', NOW()),
('Tag3', NOW()),
('Tag4', NOW()),
('Tag5', NOW()),
('Tag6', NOW()),
('Tag7', NOW()),
('Tag8', NOW()),
('Tag9', NOW()),
('Tag10', NOW());


INSERT INTO post_tags (post_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 1),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);


INSERT INTO notifications (user_id, message, read_status, created_at) VALUES
(1, 'You have a new follower!', FALSE, NOW()),
(2, 'Your post was liked!', TRUE, NOW()),
(3, 'New message from User 1', FALSE, NOW()),
(4, 'Comment on your post', FALSE, NOW()),
(5, 'You have a new notification', TRUE, NOW());


INSERT INTO user_settings (user_id, setting_key, setting_value) VALUES
(1, 'theme', 'dark'),
(2, 'notifications', 'enabled'),
(3, 'language', 'English'),
(4, 'timezone', 'GMT+0'),
(5, 'privacy', 'public');


INSERT INTO reports (user_id, post_id, comment_id, reason, created_at) VALUES
(1, 1, NULL, 'Inappropriate content', NOW()),
(2, NULL, 2, 'Spam', NOW()),
(3, 3, NULL, 'Offensive language', NOW()),
(4, NULL, 1, 'Harassment', NOW()),
(5, 5, NULL, 'Fake news', NOW());

