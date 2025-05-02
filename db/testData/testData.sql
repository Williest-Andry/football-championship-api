INSERT INTO season (season_id, begin_date, end_date) VALUES
    ('e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b', '2024-09-01', '2025-06-30');

INSERT INTO league (league_id, league_name, country) VALUES
    ('d4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b', 'Spain', 'LA_LIGA');

INSERT INTO league_season (season_id, league_id) VALUES
    ('e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b', 'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b');



INSERT INTO coach (coach_id, coach_name, coach_nationality) VALUES
    ('a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'Hansi Flick', 'Germany');

INSERT INTO ranking (ranking_id, league_id, rank, points) VALUES
    ('c1d2e3f4-a5b6-c7d8-e9f0-1a2b3c4d5e6f', 'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b', 2, 72);

INSERT INTO club (
    club_id,
    league_id,
    coach_id,
    ranking_id,
    club_name,
    creation_year,
    acronym,
    stadium_name
) VALUES (
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             'a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d',
             'c1d2e3f4-a5b6-c7d8-e9f0-1a2b3c4d5e6f',
             'FC Barcelona',
             '1899-11-29',
             'FCB',
             'Lluís Companys'
         );

INSERT INTO player (
    player_id, club_id, player_name, player_number,
    player_nationality, player_birthday, player_position
) VALUES
-- Robert Lewandowski
('d8a1b69c-2499-4fd2-8d0c-28cfbd1f5ed0', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Robert Lewandowski', 9, 'Pologne', '1988-08-21', 'STRIKER'),

-- Pedri
('e3c8d9d1-f2ac-4a6e-90db-0a561827b389', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Pedri González', 8, 'Espagne', '2002-11-25', 'MIDFIELDER'),

-- Gavi
('0f87f6e2-ae38-4983-9252-cd8b589f2a5b', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Gavi', 6, 'Espagne', '2004-08-05', 'MIDFIELDER'),

-- Frenkie de Jong
('c59de13d-e70b-44d0-8d93-45e51d9c5fa3', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Frenkie de Jong', 21, 'Pays-Bas', '1997-05-12', 'MIDFIELDER'),

-- Ter Stegen
('a7d436e8-0af5-4f11-a1f6-0e6b0e4ff1aa', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Marc-André ter Stegen', 1, 'Allemagne', '1992-04-30', 'GOALKEEPER');


INSERT INTO coach (coach_id, coach_name, coach_nationality) VALUES
    ('bbccddee-1234-5678-90ab-cdef12345678', 'Carlo Ancelotti', 'Italy');

INSERT INTO ranking (ranking_id, league_id, rank, points) VALUES
    ('99887766-5544-3322-1100-aabbccddeeff', 'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b', 1, 78);

INSERT INTO club (
    club_id,
    league_id,
    coach_id,
    ranking_id,
    club_name,
    creation_year,
    acronym,
    stadium_name
) VALUES (
             'fedcba98-7654-3210-bcde-9876543210ff',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             'bbccddee-1234-5678-90ab-cdef12345678',
             '99887766-5544-3322-1100-aabbccddeeff',
             'Real Madrid',
             '1902-03-06',
             'RMA',
             'Santiago Bernabéu'
         );

INSERT INTO match (
    match_id,
    league_id,
    match_date,
    home_team,
    away_team
) VALUES (
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             '2025-04-12 20:00:00',
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', -- FC Barcelona
             'fedcba98-7654-3210-bcde-9876543210ff'  -- Real Madrid
         );

INSERT INTO player (
    player_id,
    club_id,
    player_name,
    player_number,
    player_nationality,
    player_birthday,
    player_position
) VALUES
-- Vinicius Junior
('10000000-aaaa-bbbb-cccc-000000000001',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Vinicius Junior',
 7,
 'Bresil',
 '2000-07-12',
 'STRIKER'),

-- Jude Bellingham
('10000000-aaaa-bbbb-cccc-000000000002',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Jude Bellingham',
 5,
 'Angleterre',
 '2003-06-29',
 'MIDFIELDER'),

-- Luka Modric
('10000000-aaaa-bbbb-cccc-000000000003',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Luka Modric',
 10,
 'Croatie',
 '1985-09-09',
 'MIDFIELDER'),

-- Antonio Rudiger
('10000000-aaaa-bbbb-cccc-000000000004',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Antonio Rudiger',
 22,
 'Allemagne',
 '1993-03-03',
 'DEFENDER'),

-- Thibaut Courtois
('10000000-aaaa-bbbb-cccc-000000000005',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Thibaut Courtois',
 1,
 'Belgique',
 '1992-05-11',
 'GOALKEEPER');
