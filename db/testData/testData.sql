INSERT INTO league (league_id, league_name, country) VALUES
    ('d4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b', 'LA_LIGA', 'Spain');

-- Saison 2024 (UUID fourni)
INSERT INTO season VALUES (
                              'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                              '2024',
                              'S2024-2025',
                              'STARTED',
                              'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b'
                          );

-- Saison 2023
INSERT INTO season VALUES (
                              'b32a1a52-0bcd-4d8b-89c1-f3d123456789',
                              '2023',
                              'S2023-2024',
                              'FINISHED',
                              'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b'
                          );

-- Saison 2025
INSERT INTO season VALUES (
                              '0e72dce5-6e34-4919-8f6d-afe112233445',
                              '2025',
                              'S2025-2026',
                              'NOT_STARTED',
                              'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b'
                          );

-- Saison 2022
INSERT INTO season VALUES (
                              '5c8e37d9-fbd4-4e78-a5c4-dc3344556677',
                              '2022',
                              'S2022-2023',
                              'FINISHED',
                              'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b'
                          );

-- Saison 2026
INSERT INTO season VALUES (
                              '9a1b2c3d-4e5f-6789-aaaa-bbccddeeff00',
                              '2026',
                              'S2026-2027',
                              'NOT_STARTED',
                              'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b'
                          );


INSERT INTO coach (coach_id, coach_name, coach_nationality) VALUES
    ('a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'Hansi Flick', 'Germany');


INSERT INTO club (
    club_id,
    league_id,
    coach_id,
    club_name,
    creation_year,
    acronym,
    stadium_name
) VALUES (
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             'a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d',
             'FC Barcelona',
             '1899',
             'FCB',
             'Lluís Companys'
         );

INSERT INTO player (
    player_id, club_id, player_name, player_number,
    player_nationality, player_birth_year, player_position
) VALUES
-- Robert Lewandowski
('d8a1b69c-2499-4fd2-8d0c-28cfbd1f5ed0', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Robert Lewandowski', 9, 'Pologne', '1988', 'STRIKER'),

-- Pedri
('e3c8d9d1-f2ac-4a6e-90db-0a561827b389', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Pedri González', 8, 'Espagne', '2002', 'MIDFIELDER'),

-- Gavi
('0f87f6e2-ae38-4983-9252-cd8b589f2a5b', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Gavi', 6, 'Espagne', '2004', 'MIDFIELDER'),

-- Frenkie de Jong
('c59de13d-e70b-44d0-8d93-45e51d9c5fa3', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Frenkie de Jong', 21, 'Pays-Bas', '1997', 'MIDFIELDER'),

-- Ter Stegen
('a7d436e8-0af5-4f11-a1f6-0e6b0e4ff1aa', 'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c', 'Marc-André ter Stegen', 1, 'Allemagne', '1992', 'GOALKEEPER');


INSERT INTO coach (coach_id, coach_name, coach_nationality) VALUES
    ('bbccddee-1234-5678-90ab-cdef12345678', 'Carlo Ancelotti', 'Italy');

INSERT INTO club (
    club_id,
    league_id,
    coach_id,
    club_name,
    creation_year,
    acronym,
    stadium_name
) VALUES (
             'fedcba98-7654-3210-bcde-9876543210ff',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             'bbccddee-1234-5678-90ab-cdef12345678',
             'Real Madrid',
             '1902',
             'RMA',
             'Santiago Bernabéu'
         );

INSERT INTO match (
    match_id,
    league_id,
    match_date_time,
    club_playing_home,
    club_playing_away,
    stadium,
    actual_status,
    season_id
) VALUES (
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
             'd4a3f2e1-6b5c-4d7e-a8b9-0c1d2e3f4a5b',
             '2025-04-12 20:00:00',
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             'fedcba98-7654-3210-bcde-9876543210ff',
             'Lluís Companys',
             'FINISHED',
             'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b'
         );


INSERT INTO player (
    player_id,
    club_id,
    player_name,
    player_number,
    player_nationality,
    player_birth_year,
    player_position
) VALUES
-- Vinicius Junior
('10000000-aaaa-bbbb-cccc-000000000001',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Vinicius Junior',
 7,
 'Bresil',
 '2000',
 'STRIKER'),

-- Jude Bellingham
('10000000-aaaa-bbbb-cccc-000000000002',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Jude Bellingham',
 5,
 'Angleterre',
 '2003',
 'MIDFIELDER'),

-- Luka Modric
('10000000-aaaa-bbbb-cccc-000000000003',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Luka Modric',
 10,
 'Croatie',
 '1985',
 'MIDFIELDER'),

-- Antonio Rudiger
('10000000-aaaa-bbbb-cccc-000000000004',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Antonio Rudiger',
 22,
 'Allemagne',
 '1993',
 'DEFENDER'),

-- Thibaut Courtois
('10000000-aaaa-bbbb-cccc-000000000005',
 'fedcba98-7654-3210-bcde-9876543210ff',
 'Thibaut Courtois',
 1,
 'Belgique',
 '1992',
 'GOALKEEPER');


INSERT INTO player_statistic (
    player_statistic_id,
    player_id,
    season_id,
    match_id,
    scored_goals,
    playing_time_minute
) VALUES
-- Robert Lewandowski (1 but, 90 minutes)
('20000000-aaaa-bbbb-cccc-000000000001',
 'd8a1b69c-2499-4fd2-8d0c-28cfbd1f5ed0',
 'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
 'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
 1,
 90.0),

-- Gavi (1 but, 85 minutes)
('20000000-aaaa-bbbb-cccc-000000000002',
 '0f87f6e2-ae38-4983-9252-cd8b589f2a5b',
 'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
 'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
 1,
 85.0),

-- Pedri (0 but, 88 minutes)
('20000000-aaaa-bbbb-cccc-000000000003',
 'e3c8d9d1-f2ac-4a6e-90db-0a561827b389',
 'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
 'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
 0,
 88.0),

-- Vinicius Junior (1 but, 90 minutes)
('20000000-aaaa-bbbb-cccc-000000000004',
 '10000000-aaaa-bbbb-cccc-000000000001',
 'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
 'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
 1,
 90.0),

-- Jude Bellingham (0 but, 87 minutes)
('20000000-aaaa-bbbb-cccc-000000000005',
 '10000000-aaaa-bbbb-cccc-000000000002',
 'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
 'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
 0,
 87.0);


-- FC Barcelona
INSERT INTO club_statistic (
    club_statistic_id,
    club_id,
    season_id,
    match_id,
    scored_goals,
    conceded_goals
) VALUES (
             '30000000-aaaa-bbbb-cccc-000000000001',
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
             2,
             1
         );

-- Real Madrid
INSERT INTO club_statistic (
    club_statistic_id,
    club_id,
    season_id,
    match_id,
    scored_goals,
    conceded_goals
) VALUES (
             '30000000-aaaa-bbbb-cccc-000000000002',
             'fedcba98-7654-3210-bcde-9876543210ff',
             'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
             1,
             2
         );

-- Frenkie de Jong (FCB) — 0 but, 90 minutes
INSERT INTO player_statistic VALUES (
                                        '20000000-aaaa-bbbb-cccc-000000000006',
                                        'c59de13d-e70b-44d0-8d93-45e51d9c5fa3',
                                        'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                                        'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
                                        0,
                                        90.0
                                    );

-- Marc-Andre ter Stegen (FCB) — 0 but, 90 minutes
INSERT INTO player_statistic VALUES (
                                        '20000000-aaaa-bbbb-cccc-000000000007',
                                        'a7d436e8-0af5-4f11-a1f6-0e6b0e4ff1aa',
                                        'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                                        'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
                                        0,
                                        90.0
                                    );

-- Luka Modric (RMA) — 0 but, 65 minutes
INSERT INTO player_statistic VALUES (
                                        '20000000-aaaa-bbbb-cccc-000000000008',
                                        '10000000-aaaa-bbbb-cccc-000000000003',
                                        'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                                        'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
                                        0,
                                        65.0
                                    );

-- Antonio Rudiger (RMA) — 0 but, 90 minutes
INSERT INTO player_statistic VALUES (
                                        '20000000-aaaa-bbbb-cccc-000000000009',
                                        '10000000-aaaa-bbbb-cccc-000000000004',
                                        'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                                        'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
                                        0,
                                        90.0
                                    );

-- Thibaut Courtois (RMA) — 0 but, 90 minutes
INSERT INTO player_statistic VALUES (
                                        '20000000-aaaa-bbbb-cccc-000000000010',
                                        '10000000-aaaa-bbbb-cccc-000000000005',
                                        'e7f9c2b1-3d4a-47e8-9f3b-2c5d6e7f8a9b',
                                        'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8',
                                        0,
                                        90.0
                                    );


-- Robert Lewandowski (FCB)
INSERT INTO goal (
    goal_id,
    club_id,
    player_id,
    score,
    minute_of_goal,
    own_goal,
    match_id
) VALUES (
             '30000000-aaaa-bbbb-cccc-000000000001',
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             'd8a1b69c-2499-4fd2-8d0c-28cfbd1f5ed0',
             1,
             34,
             FALSE,
            'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8'
         );

-- Gavi (FCB)
INSERT INTO goal (
    goal_id,
    club_id,
    player_id,
    score,
    minute_of_goal,
    own_goal,
    match_id
) VALUES (
             '30000000-aaaa-bbbb-cccc-000000000002',
             'f1e2d3c4-b5a6-7890-1b2c-3d4e5f6a7b8c',
             '0f87f6e2-ae38-4983-9252-cd8b589f2a5b',
             1,
             52,
             FALSE,
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8'
         );

-- Vinicius Junior (RMA)
INSERT INTO goal (
    goal_id,
    club_id,
    player_id,
    score,
    minute_of_goal,
    own_goal,
    match_id
) VALUES (
             '30000000-aaaa-bbbb-cccc-000000000003',
             'fedcba98-7654-3210-bcde-9876543210ff',
             '10000000-aaaa-bbbb-cccc-000000000001',
             1,
             76,
             FALSE,
             'a9b8c7d6-e5f4-3210-b1c2-d3e4f5a6b7c8'
         );


INSERT INTO coach (
    coach_id,
    coach_name,
    coach_nationality
) VALUES (
             '8b1fc7d5-0b7d-4219-b3ce-4131ea550e78',
             'Diego Simeone',
             'Argentina'
         );
