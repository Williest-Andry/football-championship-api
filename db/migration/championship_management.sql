DROP DATABASE IF EXISTS championship_management;
CREATE DATABASE championship_management;
\c championship_management

CREATE TYPE league_country AS ENUM ('England', 'Spain', 'Germany', 'Italy', 'France');
CREATE TYPE league_name AS ENUM('PREMIER_LEAGUE', 'LA_LIGA', 'BUNDESLIGA', 'SERIA_A', 'LIGUE_1');
CREATE TABLE league(
    league_id UUID PRIMARY KEY,
    league_name league_name NOT NULL,
    country league_country NOT NULL
);

CREATE TYPE season_status AS ENUM ('NOT_STARTED', 'STARTED', 'FINISHED');
CREATE TABLE season(
    season_id UUID PRIMARY KEY,
    year VARCHAR(4) UNIQUE NOT NULL,
    alias VARCHAR(10) NOT NULL,
    status season_status NOT NULL,
    league_id UUID REFERENCES league(league_id) NOT NULL
);

CREATE TABLE coach(
    coach_id UUID PRIMARY KEY,
    coach_name VARCHAR NOT NULL,
    coach_nationality VARCHAR NOT NULL
);

CREATE TABLE club(
    club_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id) NOT NULL,
    coach_id UUID REFERENCES coach(coach_id) NOT NULL,
    club_name VARCHAR UNIQUE NOT NULL,
    creation_year VARCHAR(4) NOT NULL,
    acronym VARCHAR(3) NOT NULL,
    stadium_name VARCHAR NOT NULL
);

CREATE TYPE match_status AS ENUM ('NOT_STARTED','STARTED','FINISHED');
CREATE TABLE match(
    match_id UUID PRIMARY KEY,
    league_id UUID REFERENCES league(league_id) NOT NULL,
    season_id UUID REFERENCES season(season_id) NOT NULL,
    match_date_time TIMESTAMP,
    club_playing_home UUID REFERENCES club(club_id) NOT NULL,
    club_playing_away UUID REFERENCES club(club_id) NOT NULL,
    stadium VARCHAR NOT NULL,
    actual_status match_status NOT NULL
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
    conceded_goals INT NOT NULL
);

CREATE TABLE goal (
    goal_id UUID PRIMARY KEY,
    club_id UUID REFERENCES club(club_id),
    player_id UUID REFERENCES player(player_id),
    match_id UUID REFERENCES match(match_id),
    score INT NOT NULL,
    minute_of_goal INT NOT NULL,
    own_goal BOOLEAN NOT NULL
);