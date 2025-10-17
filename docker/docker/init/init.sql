DROP DATABASE IF EXISTS escape_room_db;
CREATE DATABASE escape_room_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE escape_room_db;

-- Room table
CREATE TABLE room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    price DECIMAL(6,2) NOT NULL
);

-- Clue table
CREATE TABLE hint (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    theme VARCHAR(100),
    room_id INT,
    price DECIMAL(6,2),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Decoration objects
CREATE TABLE decoration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    material VARCHAR(100),
    price DECIMAL(6,2),
    room_id INT,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Player table
CREATE TABLE player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);

-- Ticket table
CREATE TABLE ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    room_id INT,
    purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(6,2),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Reward table
CREATE TABLE reward (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    date_awarded DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE SET NULL
);

-- Sample rooms
INSERT INTO room (name, difficulty, price) VALUES
('The Chamber of Secrets', 'Hard', 45.00),
('Escape from Azkaban', 'Medium', 40.00),
('The Forbidden Forest', 'Easy', 35.00);

-- Sample clues
INSERT INTO hint (description, theme, room_id) VALUES
('Use Parseltongue to open the Chamber door.', 'Serpent Magic', 1),
('Find the Patronus to reveal the exit.', 'Defense Against the Dark Arts', 2),
('Follow the light of the centaurs.', 'Forest Guidance', 3);

-- Sample decorations
INSERT INTO decoration (name, material, price, room_id) VALUES
('Sorting Hat', 'Cloth', 120.00, 1),
('Flying Broom', 'Wood', 250.00, 2),
('Golden Snitch', 'Gold', 300.00, 3);

-- Sample players
INSERT INTO player (name, email) VALUES
('Harry Potter', 'harry@hogwarts.edu'),
('Hermione Granger', 'hermione@hogwarts.edu'),
('Ron Weasley', 'ron@hogwarts.edu');

-- Sample tickets
INSERT INTO ticket (player_id, room_id, price) VALUES
(1, 1, 45.00),
(2, 2, 40.00),
(3, 3, 35.00);

-- Sample rewards
INSERT INTO reward (player_id, name, description) VALUES
(1, 'Master of Parseltongue', 'Opened the Chamber of Secrets'),
(2, 'Time Turner Champion', 'Escaped Azkaban in record time'),
(3, 'Forest Explorer', 'Solved the Forbidden Forest puzzle');
