CREATE TABLE IF NOT EXISTS tb_roles (
                                        role_id INT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL
    );
INSERT IGNORE INTO tb_roles(role_id, name) values (1, 'admin');
INSERT IGNORE INTO tb_roles(role_id, name) values (2, 'basic');
