
CREATE TABLE team (
    id          SERIAL          PRIMARY KEY,
    name        VARCHAR(50)     NOT NULL
);

CREATE TABLE member (
    id          SERIAL          PRIMARY KEY,
    team_id     INTEGER         NOT NULL,
    name        VARCHAR(50)     NOT NULL
);

CREATE TABLE holiday (
    id          SERIAL          PRIMARY KEY,
    start_date  DATE            NOT NULL,
    end_date    DATE            NOT NULL
);