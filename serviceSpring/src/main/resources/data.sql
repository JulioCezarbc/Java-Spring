INSERT INTO usuarios (nome, login, senha, email, situacao) VALUES ('julio', 'login','senha','julio@gmail.com', 'ATIVO');

INSERT INTO perfil (descricao) VALUES ('Adm');
INSERT INTO perfil (descricao) VALUES ('Gerente');
INSERT INTO perfil (descricao) VALUES ('Cliente');

INSERT INTO recursos (nome,chave) VALUES ('tela usuario', 'usuario');
INSERT INTO recursos (nome,chave) VALUES ('tela perfil', 'perfil');
INSERT INTO recursos (nome,chave) VALUES ('tela recurso', 'recurso');