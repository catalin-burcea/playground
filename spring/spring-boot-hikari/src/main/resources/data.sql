DROP TABLE IF EXISTS cities;

CREATE TABLE cities (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  state VARCHAR(250) NOT NULL
);

INSERT INTO cities (name, state) VALUES
  ('Bucharest', 'Romania'),
  ('Budapest', 'Hungary'),
  ('Bratislava', 'Slovakia');