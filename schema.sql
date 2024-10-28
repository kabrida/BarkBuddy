DROP TABLE IF EXISTS dog CASCADE;
DROP TABLE IF EXISTS breed CASCADE;
DROP TABLE IF EXISTS owner CASCADE;

CREATE TABLE owner (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    owner_dob DATE NOT NULL,
    user_role VARCHAR(100)
);

INSERT INTO owner (username, password_hash, first_name, last_name, owner_dob, user_role) VALUES 
('user', '$2y$10$6HSLzVz6tRDHxtlK5KW9JuqqzakSfeiWLZPuglXn4.i1v4C1HD6qK', 'Matti', 'Meikäläinen', '1985-05-15', 'USER'),
('admin', '$2y$10$9dMOLjy71gnW3NZ.NTTWYuU0W3hrwz2RPY8p8YmrhPDNAwXPY5.YW', 'Maija', 'Mehiläinen', '1990-10-10', 'ADMIN');

CREATE TABLE breed (
    id BIGSERIAL PRIMARY KEY,
    breed_name VARCHAR(100) NOT NULL,
    bred_for VARCHAR(255),
    breed_group VARCHAR(255),
    life_span VARCHAR(50),
    temperament VARCHAR(500),
    origin VARCHAR(500),
    weight_metric VARCHAR(50),
    height_metric VARCHAR(50),
    reference_image_id VARCHAR(255)
);


CREATE TABLE dog (
    id BIGSERIAL PRIMARY KEY,
    dog_name VARCHAR(100) NOT NULL,
    breed_id BIGINT NOT NULL,
    reg_num VARCHAR(100) UNIQUE NOT NULL,
    dog_dob DATE NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (breed_id) REFERENCES breed(id),
    FOREIGN KEY (owner_id) REFERENCES owner(id)
);

