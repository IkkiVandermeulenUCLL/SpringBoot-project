DROP TABLE IF EXISTS MY_ANIMALS;
DROP TABLE IF EXISTS MY_STABLES;

CREATE TABLE MY_STABLES(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(255),
    CAPACITY    INT
);

CREATE TABLE MY_ANIMALS(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(255),
    AGE         INT,
    STABLE_ID   BIGINT,
    FOREIGN KEY (STABLE_ID) REFERENCES MY_STABLES
);
