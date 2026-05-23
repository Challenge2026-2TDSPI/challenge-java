INSERT INTO tutor (nome, cpf, email, telefone)
VALUES ('Admin PetPath', '52998224725', 'admin@petpath.com', '11999999999');

INSERT INTO usuario (fk_tutor, rm, senha, permissao, data_criacao, status)
VALUES (1, 'rm000001', '$2a$10$Ru73ELV/4tecjqOvTttLRucUpqNlNP1Ohu1bOxkxaU33kTj7kCzXe', 'USER', '2025-01-01', 'ATIVO');
