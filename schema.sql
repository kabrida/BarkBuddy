CREATE TABLE breed (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL 
);

CREATE TABLE owner (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, 
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL
);

CREATE TABLE dog (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    breed_id INTEGER REFERENCES breed(id) ON DELETE CASCADE,
    owner_id INTEGER REFERENCES owner(id) ON DELETE CASCADE,
    birth_date DATE NOT NULL
);