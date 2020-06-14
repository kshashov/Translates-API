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

-- test data
INSERT INTO users (id, email, name, role_id) VALUES (0, 'envoy93@gmail.com', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (1, 'envoy93@gmail.com1', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (2, 'envoy93@gmail.com2', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (3, 'envoy93@gmail.com3', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (4, 'envoy93@gmail.com4', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (5, 'envoy93@gmail.com5', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (6, 'envoy93@gmail.com6', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (7, 'envoy93@gmail.com7', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (8, 'envoy93@gmail.com8', 'user', 3);
INSERT INTO users (id, email, name, role_id) VALUES (9, 'envoy93@gmail.com9', 'user', 3);