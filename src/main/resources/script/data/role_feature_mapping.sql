INSERT INTO role_feature_mapping(role_id, feature_id, is_active) VALUES 
-- Admin (role_id=1) - All features
(1, 1, 1), (1, 2, 1), (1, 3, 1), (1, 4, 1), (1, 5, 1), (1, 6, 1), (1, 7, 1), (1, 8, 1), (1, 9, 1), (1, 10, 1), 
(1, 11, 1), (1, 12, 1), (1, 13, 1), (1, 14, 1), (1, 15, 1), (1, 16, 1), (1, 17, 1), (1, 18, 1), (1, 19, 1), (1, 20, 1), (1, 21, 1),

-- Election Officer (role_id=2) - Election management features
(2, 7, 1), (2, 8, 1), (2, 9, 1), (2, 10, 1), (2, 11, 1), (2, 13, 1), (2, 14, 1),

-- Voter (role_id=3) - Voting and profile features
(3, 12, 1), (3, 13, 1), (3, 14, 1), (3, 15, 1),

-- Moderator (role_id=4) - Monitoring features
(4, 16, 1), (4, 17, 1), (4, 13, 1), (4, 14, 1),

-- Candidate (role_id=5) - Campaign features
(5, 18, 1), (5, 19, 1), (5, 13, 1), (5, 14, 1),

-- Observer (role_id=6) - Observation features
(6, 20, 1), (6, 21, 1), (6, 14, 1),

-- Logout feature for all roles
(1, 22, 1), (2, 22, 1), (3, 22, 1), (4, 22, 1), (5, 22, 1), (6, 22, 1);
