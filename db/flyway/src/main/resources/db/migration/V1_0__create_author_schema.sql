DROP TABLE IF EXISTS author;

CREATE TABLE author (
  id             INT          NOT NULL PRIMARY KEY,
  first_name     VARCHAR(50),
  last_name      VARCHAR(50)  NOT NULL
);