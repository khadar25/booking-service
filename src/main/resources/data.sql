CREATE TABLE movie (
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(100),
 language VARCHAR(50),
 genre VARCHAR(50)
);
CREATE TABLE theatre (
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(100),
 city VARCHAR(100)
);
CREATE TABLE screen (
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 screen_name VARCHAR(50),
 theatre_id BIGINT
);
CREATE TABLE seat (
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 screen_id BIGINT,
 seat_number VARCHAR(10),
 seat_type VARCHAR(20)
);

CREATE TABLE show(
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 movie_id BIGINT,
 screen_id BIGINT,
 show_date DATE,
 show_time TIME
);

CREATE TABLE booking (
 id BIGINT  AUTO_INCREMENT PRIMARY KEY,
 show_id BIGINT,
 user_name VARCHAR(100),
 booking_time TIMESTAMP
);

-- Insert dummy data into movie
INSERT INTO movie(id, name, language, genre) VALUES
(1, 'Avengers', 'English', 'Action'),
(2, 'Pushpa', 'Telugu', 'Action');

-- Insert dummy data into theatre
INSERT INTO theatre(id, name, city) VALUES
(1, 'PVR Orion', 'Bangalore'),
(2, 'INOX Garuda', 'Bangalore'),
(3, 'Cinepolis Forum', 'Hyderabad');

-- Insert dummy data into screen
INSERT INTO screen(id, screen_name, theatre_id) VALUES
(1, 'Screen 1', 1),
(2, 'Screen 2', 2),
(3, 'Screen 1', 3);

-- Insert dummy data into show
INSERT INTO show(id, movie_id, screen_id, show_date, show_time) VALUES
(1, 1, 1, '2026-04-16', '18:00:00'),
(2, 2, 2, '2026-04-16', '20:00:00'),
(3, 1, 3, '2026-04-17', '17:30:00');

-- Insert dummy data into seat
INSERT INTO seat(id, screen_id, seat_number, seat_type) VALUES
(1, 1, 'A1', 'Regular'),
(2, 1, 'A2', 'Regular'),
(3, 2, 'B1', 'Premium'),
(4, 3, 'C1', 'VIP');

-- Insert dummy data into booking
INSERT INTO booking(id, show_id, user_name, booking_time) VALUES
(1, 1, 'alice', '2026-04-15 10:00:00'),
(2, 2, 'bob', '2026-04-15 11:00:00'),
(3, 3, 'charlie', '2026-04-15 12:00:00');
