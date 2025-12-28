INSERT INTO feature(id, name, description, menu_id, menu_name, url, icon, is_active) VALUES 
-- Admin Features
(1, 'Dashboard', 'Admin Dashboard', 1, 'Admin Panel', '/admin/dashboard', 'fas fa-tachometer-alt', 1),
(2, 'User Management', 'Manage all users', 1, 'Admin Panel', '/admin/users', 'fas fa-users', 1),
(3, 'Role Management', 'Manage user roles', 1, 'Admin Panel', '/admin/roles', 'fas fa-user-shield', 1),
(4, 'System Settings', 'Configure system settings', 1, 'Admin Panel', '/admin/settings', 'fas fa-cogs', 1),
(5, 'Reports', 'View system reports', 1, 'Admin Panel', '/admin/reports', 'fas fa-chart-bar', 1),
(6, 'Audit Logs', 'View audit trails', 1, 'Admin Panel', '/admin/logs', 'fas fa-file-alt', 1),

-- Election Officer Features
(7, 'Election Creation', 'Manage elections', 2, 'Election Management', 'create_election', 'fas fa-vote-yea', 1),
(8, 'Candidate Management', 'Manage candidates', 2, 'Election Management', '/officer/candidates', 'fas fa-user-tie', 1),
(9, 'Voter Verification', 'Verify voter eligibility', 2, 'Election Management', 'voter_verification', 'fas fa-user-check', 1),
(10, 'Election Results', 'View and manage results', 2, 'Election Management', '/officer/results', 'fas fa-trophy', 1),
(11, 'Election Statistics', 'View election statistics', 2, 'Election Management', '/officer/statistics', 'fas fa-chart-pie', 1),
(23, 'Election Verification', 'Election Verification', 2, 'Election Management', 'election_verification', 'fas fa-chart-pie', 1),

-- Voter Features
(12, 'Vote', 'Cast your vote', 3, 'Voter Portal', '/voter/vote', 'fas fa-check-square', 1),
(13, 'My Profile', 'View and edit profile', 3, 'Voter Portal', '/voter/profile', 'fas fa-user', 1),
(14, 'Election Info', 'View election information', 3, 'Voter Portal', '/voter/elections', 'fas fa-info-circle', 1),
(15, 'Voting History', 'View voting history', 3, 'Voter Portal', '/voter/history', 'fas fa-history', 1),
(24, 'Party', 'Add Party', 3, 'Voter Portal', 'party', 'fas fa-history', 1),
(25, 'Candidate Form', 'Add Candidate', 3, 'Voter Portal', 'candidate_form', 'fas fa-history', 1),

-- Moderator Features
(16, 'Monitor Voting', 'Monitor ongoing elections', 4, 'Moderator Panel', '/moderator/monitor', 'fas fa-eye', 1),
(17, 'Voting Reports', 'Generate voting reports', 4, 'Moderator Panel', '/moderator/reports', 'fas fa-file-pdf', 1),

-- Candidate Features
(18, 'My Campaign', 'Manage campaign information', 5, 'Candidate Panel', '/candidate/campaign', 'fas fa-bullhorn', 1),
(19, 'Campaign Statistics', 'View campaign statistics', 5, 'Candidate Panel', '/candidate/stats', 'fas fa-chart-line', 1),

-- Observer Features
(20, 'Election Monitoring', 'Monitor election process', 6, 'Observer Panel', '/observer/monitor', 'fas fa-binoculars', 1),
(21, 'Election Reports', 'View election reports', 6, 'Observer Panel', '/observer/reports', 'fas fa-clipboard-list', 1),

-- Profile/Auth Features (available to all roles)
(22, 'Logout', 'Sign out of the system', 3, 'Profile', '/logout', 'fas fa-sign-out-alt', 1);
