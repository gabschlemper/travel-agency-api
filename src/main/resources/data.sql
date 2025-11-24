-- Script para criação do banco de dados e dados iniciais

-- Criar banco de dados (execute manualmente no PostgreSQL)
-- CREATE DATABASE agencia_viagem;

-- As tabelas serão criadas automaticamente pelo Hibernate (spring.jpa.hibernate.ddl-auto=update)

-- Dados iniciais (opcional - podem ser inseridos via API)
-- Usuário ADMIN (senha: admin123)
-- Senha criptografada com BCrypt
INSERT INTO usuarios (nome, email, senha, perfil, ativo) 
VALUES ('Administrador', 'admin@agenciaviagem.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', true)
ON CONFLICT (email) DO NOTHING;

-- Usuário comum (senha: user123)
INSERT INTO usuarios (nome, email, senha, perfil, ativo) 
VALUES ('Usuário Teste', 'user@agenciaviagem.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Rogg.F.W.f.CoNjkYvM8Uuwe', 'USER', true)
ON CONFLICT (email) DO NOTHING;

-- Destinos de exemplo
INSERT INTO destinos (nome, localizacao, descricao, avaliacao_media, total_avaliacoes) 
VALUES 
    ('Paris', 'França', 'A Cidade Luz, famosa pela Torre Eiffel, Louvre e gastronomia excepcional', 9.5, 150),
    ('Rio de Janeiro', 'Brasil', 'Cidade maravilhosa com praias icônicas, Cristo Redentor e Pão de Açúcar', 9.2, 200),
    ('Tóquio', 'Japão', 'Capital japonesa que mescla tradição milenar com tecnologia de ponta', 9.0, 120),
    ('Barcelona', 'Espanha', 'Cidade com arquitetura única de Gaudí, praias mediterrâneas e vida noturna', 8.8, 95),
    ('Nova York', 'Estados Unidos', 'A cidade que nunca dorme, com Times Square e Estátua da Liberdade', 9.3, 180)
ON CONFLICT DO NOTHING;
