DROP DATABASE IF EXISTS championship_management ;
CREATE DATABASE championship_management;
\c championship_management

CREATE TABLE season(
    season_id UUID PRIMARY KEY,
    begin_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL
);

CREATE TYPE league_country AS ENUM ('England', 'Spain', 'Germany', 'Italy', 'France');
CREATE TABLE league(
    league_id UUID PRIMARY KEY,
    league_name VARCHAR NOT NULL,
    country league_country NOT NULL
);

CREATE TABLE league_season(
    season_id UUID REFERENCES season(season_id),
    league_id UUID REFERENCES league(league_id)
);

-- CREATE TABLE match(
--     match_id UUID PRIMARY KEY,
--     league_id UUID REFERENCES league(league_id),
--     match_date TIMESTAMP NOT NULL,
--     home_team VARCHAR NOT NULL,
--     away_team VARCHAR NOT NULL
-- );

-- CREATE TABLE