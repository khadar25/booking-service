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
 theatre_id BIGINT,
 capacity INT
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
 booking_time TIMESTAMP,
 status VARCHAR(50),
 total_price DOUBLE,
 seats TEXT  -- For storing list of seat numbers as comma-separated or JSON
);
CREATE TABLE booked_seat (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 seat_number VARCHAR(10),
 show_id BIGINT,
 booking_id BIGINT
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
INSERT INTO screen(id, screen_name, theatre_id, capacity) VALUES
(1, 'Screen 1', 1, 200),
(2, 'Screen 2', 2, 150),
(3, 'Screen 1', 3, 180);

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
INSERT INTO booking(id, show_id, user_name, booking_time, status, total_price, seats) VALUES
(1, 1, 'alice', '2026-04-15 10:00:00', 'confirmed', 15.00, 'A1,A2'),
(2, 2, 'bob', '2026-04-15 11:00:00', 'confirmed', 20.00, 'B1'),
(3, 3, 'charlie', '2026-04-15 12:00:00', 'pending', 0.00, '');

-- Insert dummy data into booked_seat
INSERT INTO booked_seat(id, seat_number, show_id, booking_id) VALUES
(1, 'A1', 1, 1),
(2, 'A2', 1, 1),
(3, 'B1', 2, 2),
(4, 'C1', 3, 3);

-- Add foreign key constraints
ALTER TABLE screen ADD CONSTRAINT fk_screen_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id);
ALTER TABLE seat ADD CONSTRAINT fk_seat_screen FOREIGN KEY (screen_id) REFERENCES screen(id);
ALTER TABLE show ADD CONSTRAINT fk_show_movie FOREIGN KEY (movie_id) REFERENCES movie(id);
ALTER TABLE show ADD CONSTRAINT fk_show_screen FOREIGN KEY (screen_id) REFERENCES screen(id);
ALTER TABLE booking ADD CONSTRAINT fk_booking_show FOREIGN KEY (show_id) REFERENCES show(id);
ALTER TABLE booked_seat ADD CONSTRAINT fk_booked_seat_show FOREIGN KEY (show_id) REFERENCES show(id);
ALTER TABLE booked_seat ADD CONSTRAINT fk_booked_seat_booking FOREIGN KEY (booking_id) REFERENCES booking(id);
