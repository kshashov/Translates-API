INSERT INTO permissions (id, code) VALUES (0, 'solve_exercises');
INSERT INTO permissions (id, code) VALUES (1, 'comment_exercises');
INSERT INTO permissions (id, code) VALUES (2, 'manage_exercises');
INSERT INTO permissions (id, code) VALUES (3, 'manage_users');

INSERT INTO roles (id, code, title, description) VALUES (0, 'inactive', 'Inactive', '');
INSERT INTO roles (id, code, title, description) VALUES (1, 'user', 'User', '');
INSERT INTO roles (id, code, title, description) VALUES (2, 'moderator', 'Moderator', '');
INSERT INTO roles (id, code, title, description) VALUES (3, 'admin', 'Admin', '');

-- inactive
INSERT INTO roles_permissions (role_id, permission_id) VALUES (0, 0);
-- user
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 0);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1);
-- moderator
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 0);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 2);
-- admin
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 0);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 2);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 3);