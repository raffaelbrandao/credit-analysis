-- ROLE
INSERT INTO credit_analysis.role (id, name) VALUES (1, 'USER'), (2, 'ANALYST');

--PRIVILEGE
INSERT INTO credit_analysis.privilege (id, name) VALUES (1, 'PROPOSAL_CREATE'), (2, 'PROPOSAL_SEARCH'),
(3, 'PROPOSAL_VIEW'), (4, 'PROPOSAL_EDIT'), (5, 'PROPOSAL_DELETE'), (6, 'PROPOSAL_STATE');

-- USER
INSERT INTO credit_analysis.user_account (id, name, username, password, account_non_locked,
account_non_expired, credentials_non_expired, enabled)
VALUES (1, 'Pedro', 'pedro.silva', 123456, TRUE, TRUE, TRUE, TRUE),
(2, 'Maria', 'maria.souza', 789456, TRUE, TRUE, TRUE, TRUE);

-- ROLES_PRIVILEGES
INSERT INTO credit_analysis.roles_privileges (role_id, privilege_id) VALUES (1, 1), (1, 2), (1, 3),
(1, 4), (1, 5), (2, 2), (2, 3), (2, 6);

-- USERS_ROLES
INSERT INTO credit_analysis.users_roles (user_id, role_id) VALUES (1, 1), (2, 2);
