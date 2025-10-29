DROP DATABASE IF EXISTS escape_room_db;
CREATE DATABASE escape_room_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE escape_room_db;

CREATE TABLE escape_room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    escape_room_id INT,
    FOREIGN KEY (escape_room_id) REFERENCES escape_room(id) ON DELETE CASCADE
);

CREATE TABLE hint (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    theme VARCHAR(100),
    room_id INT,
    price DECIMAL(10,2),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE decoration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    material VARCHAR(100),
    price DECIMAL(10,2),
    room_id INT,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    subscribed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    room_id INT,
    purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10,2),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE reward (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    date_awarded DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE SET NULL
);

-- Example data
INSERT INTO escape_room (name) VALUES ('Hogwarts Escape Adventure');

INSERT INTO room (name, difficulty, price, escape_room_id) VALUES
('The Chamber of Secrets', 'HARD', 45.00, 1),
('Escape from Azkaban', 'MEDIUM', 40.00, 1),
('The Forbidden Forest', 'EASY', 35.00, 1);

INSERT INTO hint (description, theme, room_id, price) VALUES
('Use Parseltongue to open the Chamber door.', 'Serpent Magic', 1, 1.00),
('Find the Patronus to reveal the exit.', 'Defense Against the Dark Arts', 2, 1.00),
('Follow the light of the centaurs.', 'Forest Guidance', 3, 1.00);

INSERT INTO decoration (name, material, price, room_id) VALUES
('Sorting Hat', 'Cloth', 120.00, 1),
('Flying Broom', 'Wood', 250.00, 2),
('Golden Snitch', 'Gold', 300.00, 3);

INSERT INTO player (name, email, subscribed) VALUES
('Harry Potter', 'harry@example.com', TRUE),
('Hermione Granger', 'hermione@example.com', FALSE),
('Ron Weasley', 'ron@example.com', TRUE);

INSERT INTO ticket (player_id, room_id, price) VALUES
(1, 1, 45.00),
(2, 2, 40.00),
(3, 3, 35.00);

INSERT INTO reward (player_id, name, description) VALUES
(1, 'Master of Parseltongue', 'Opened the Chamber of Secrets'),
(2, 'Time Turner Champion', 'Escaped Azkaban in record time'),
(3, 'Forest Explorer', 'Solved the Forbidden Forest puzzle');
