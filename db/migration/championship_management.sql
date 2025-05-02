DROP DATABASE IF EXISTS championship_management;
CREATE DATABASE championship_management;
\c championship_management

CREATE TABLE season(
    season_id UUID PRIMARY KEY,
    begin_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TYPE league_country AS ENUM ('England', 'Spain', 'Germany', 'Italy', 'France');
CREATE TYPE league_name AS ENUM('PREMIER_LEAGUE', 'LA_LIGA', 'BUNDESLIGA', 'SERIA_A', 'LIGUE_1');
CREATE TABLE league(
    league_id UUID PRIMARY KEY,
    league_name league_name NOT NULL,
    country league_country NOT NULL
);

CREATE TABLE league_season(
    season_id UUID REFERENCES season(season_id) NOT NULL,
    league_id UUID REFERENCES league(league_id)
);

CREATE TABLE coach(
    coach_id UUID PRIMARY KEY,
    coach_name VARCHAR NOT NULL,
    coach_nationality VARCHAR NOT NULL
);

CREATE TABLE ranking(
    ranking_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id) NOT NULL,
    rank INT UNIQUE NOT NULL,
    points INT NOT NULL
);

CREATE TABLE club(
    club_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id) NOT NULL,
    coach_id UUID REFERENCES coach(coach_id) NOT NULL,
    ranking_id UUID REFERENCES ranking(ranking_id) NOT NULL,
    club_name VARCHAR UNIQUE NOT NULL,
    creation_year VARCHAR(4) NOT NULL,
    acronym VARCHAR(3) NOT NULL,
    stadium_name VARCHAR NOT NULL
);

CREATE TABLE match(
    match_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id) NOT NULL,
    match_date TIMESTAMP,
    home_team UUID REFERENCES club(club_id) NOT NULL,
    away_team UUID REFERENCES club(club_id) NOT NULL
);

CREATE TYPE player_position_in_field AS ENUM ('STRIKER', 'MIDFIELDER', 'DEFENDER', 'GOALKEEPER');
CREATE TABLE player(
    player_id UUID PRIMARY KEY,
    club_id UUID REFERENCES club(club_id),
    player_name VARCHAR NOT NULL,
    player_number INT NOT NULL,
    player_nationality VARCHAR NOT NULL,
    player_birth_year VARCHAR(4) NOT NULL,
    player_position player_position_in_field NOT NULL
);

CREATE TABLE player_statistic(
    player_statistic_id UUID PRIMARY KEY,
    player_id UUID REFERENCES player(player_id) NOT NULL,
    season_id UUID REFERENCES season(season_id) NOT NULL,
    match_id UUID REFERENCES match(match_id) NOT NULL,
    scored_goals INT NOT NULL,
    playing_time_minute DECIMAL NOT NULL
);

CREATE TABLE club_statistic(
    club_statistic_id UUID PRIMARY KEY,
    club_id UUID REFERENCES club(club_id),
    season_id UUID REFERENCES season(season_id) NOT NULL,
    match_id UUID REFERENCES match(match_id) NOT NULL,
    score_goals INT NOT NULL,
    conceded_goals INT NOT NULL,
    difference_goals INT NOT NULL,
    clean_sheet_number INT NOT NULL
);