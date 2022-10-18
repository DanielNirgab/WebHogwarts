CREATE TABLE cars (
    car_id REAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    cost REAL CHECK ( cost > 0 )
);

CREATE TABLE humans (
    human_id REAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INT NOT NULL CHECK ( age > 0 ),
    driver_licence BOOLEAN DEFAULT FALSE,
    car_id INT REFERENCES cars(car_id)
);