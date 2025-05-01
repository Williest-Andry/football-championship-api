DROP DATABASE IF EXISTS championship_management;
CREATE DATABASE championship_management;
\c championship_management

CREATE TABLE season(
    season_id UUID PRIMARY KEY,
    begin_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL
);

CREATE TYPE league_country AS ENUM ('England', 'Spain', 'Germany', 'Italy', 'France');
CREATE TYPE league_name AS ENUM('PREMIER_LEAGUE', 'LA_LIGA', 'BUNDESLIGA', 'SERIA_A', 'LIGUE_1');
CREATE TABLE league(
    league_id UUID PRIMARY KEY,
    league_name league_country NOT NULL,
    country league_name NOT NULL
);

CREATE TABLE league_season(
    season_id UUID REFERENCES season(season_id),
    league_id UUID REFERENCES league(league_id)
);

CREATE TABLE coach(
    coach_id UUID PRIMARY KEY,
    coach_name VARCHAR NOT NULL,
    coach_nationality VARCHAR NOT NULL
);

CREATE TABLE ranking(
    ranking_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id),
    rank INT UNIQUE NOT NULL,
    points INT NOT NULL
);

CREATE TABLE club(
    club_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id),
    coach_id UUID REFERENCES coach(coach_id),
    ranking_id UUID REFERENCES ranking(ranking_id),
    club_name VARCHAR UNIQUE NOT NULL,
    creation_year DATE NOT NULL,
    acronym VARCHAR(3) NOT NULL,
    stadium_name VARCHAR NOT NULL
);

CREATE TABLE match(
    match_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id),
    match_date TIMESTAMP NOT NULL,
    home_team UUID REFERENCES club(club_id),
    away_team UUID REFERENCES club(club_id)
);

CREATE TYPE player_position_in_field AS ENUM ('FORWARD', 'MIDFIELDER', 'DEFENDER', 'GOALKEEPER');
CREATE TABLE player(
    player_id UUID PRIMARY KEY,
    club_id UUID REFERENCES club(club_id),
    player_name VARCHAR NOT NULL,
    player_number INT NOT NULL,
    player_nationality VARCHAR NOT NULL,
    player_birthday DATE NOT NULL,
    player_position player_position_in_field NOT NULL
);