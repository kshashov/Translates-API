-- roles
INSERT INTO permissions (id, code) VALUES (0, 'SOLVE_EXERCISES');
INSERT INTO permissions (id, code) VALUES (1, 'COMMENT_EXERCISES');
INSERT INTO permissions (id, code) VALUES (2, 'MANAGE_EXERCISES');
INSERT INTO permissions (id, code) VALUES (3, 'MANAGE_USERS');

INSERT INTO roles (id, code, title, description) VALUES (0, 'INACTIVE', 'Inactive', 'Inactive');
INSERT INTO roles (id, code, title, description) VALUES (1, 'USER', 'User', 'User');
INSERT INTO roles (id, code, title, description) VALUES (2, 'MODERATOR', 'Moderator', 'Moderator');
INSERT INTO roles (id, code, title, description) VALUES (3, 'ADMIN', 'Admin', 'Admin');

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
