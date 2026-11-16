-- Banco isolado para o cadastro de núcleos do Programa Viva Esportes.
-- Não é referenciado pelo backend ou pelo frontend do portal.
-- Fonte: TABELAS BI 11-26 (1).xlsx, abas Regional, nucleo,
-- codigo_nucleo, nucleo_matriculados, modalidade e nucle_modalidade.

CREATE DATABASE IF NOT EXISTS portal_esporte_nucleos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE portal_esporte_nucleos;

CREATE TABLE IF NOT EXISTS regionais (
    codigo INT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS nucleos (
    codigo INT PRIMARY KEY,
    nome VARCHAR(180) NOT NULL UNIQUE,
    regional_codigo INT NOT NULL,
    matriculados INT UNSIGNED NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_nucleos_regionais
        FOREIGN KEY (regional_codigo) REFERENCES regionais (codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS modalidades (
    codigo INT PRIMARY KEY,
    nome VARCHAR(160) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS nucleos_modalidades (
    nucleo_codigo INT NOT NULL,
    modalidade_codigo INT NOT NULL,
    matriculados INT UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (nucleo_codigo, modalidade_codigo),
    CONSTRAINT fk_nm_nucleo
        FOREIGN KEY (nucleo_codigo) REFERENCES nucleos (codigo),
    CONSTRAINT fk_nm_modalidade
        FOREIGN KEY (modalidade_codigo) REFERENCES modalidades (codigo)
) ENGINE=InnoDB;

INSERT INTO regionais (codigo, nome) VALUES
    (1, 'Alterosas'), (2, 'Centro'), (3, 'Citrolândia'),
    (4, 'Icaivera'), (5, 'Imbiruçu'), (6, 'Norte'),
    (7, 'Petrovale'), (8, 'PTB'), (9, 'Teresópolis'),
    (10, 'Vianópolis'), (11, 'Outras Cidades')
ON DUPLICATE KEY UPDATE nome = VALUES(nome), ativo = TRUE;

INSERT INTO nucleos (codigo, nome, regional_codigo, matriculados) VALUES
    (1, 'CAIC', 5, 236),
    (2, 'CAMPO CRUZEIRO DO SUL', 1, 97),
    (3, 'CAMPO DO ALTEROSA', 1, 95),
    (4, 'CAMPO DO BAIRRO SANTA INÊS', 2, 45),
    (5, 'CAMPO DO ITACOLOMI', 1, 63),
    (6, 'CAMPO DO NOSSA SENHORA DAS GRAÇAS', 6, 130),
    (7, 'CAMPO PINGO D’ÁGUA', 2, 36),
    (8, 'CAMPO SÃO JOÃO', 2, 114),
    (9, 'CENTRO INFANTIL MUNICIPAL TIA DULCE', 9, 50),
    (10, 'CLUBE DA FIAT', 2, 64),
    (11, 'COMPLEXO RICCARDO MEDIOLI', 9, 960),
    (12, 'E. E. SIMÃO DA CUNHA', 6, 33),
    (13, 'E. M. GILBERTO ALVES', 1, 47),
    (14, 'E. M. MARIA ELENA DA CUNHA BRÁS', 4, 32),
    (15, 'E. M. MÁRIO MARCOS', 6, 105),
    (16, 'E.E. DR. ORESTES DINIZ', 3, 10),
    (17, 'E.E. SEBASTIÃO FERREIRA', 1, 42),
    (18, 'GALPÃO DA GINÁSTICA', 6, 562),
    (19, 'GINO JOSÉ DE SOUZA', 8, 65),
    (20, 'HORTO', 2, 2236),
    (21, 'HORTO ANEXO', 2, 65),
    (22, 'INSTITUTO RAMACRISNA', 10, 249),
    (23, 'JARDIM ALTEROSAS', 1, 264),
    (24, 'JARDIM PETRÓPOLIS', 2, 110),
    (25, 'MUNICIPAL PEDRO BUTTI – (BILEZÃO)', 3, 121),
    (26, 'NÚCLEO AABB', 2, 171),
    (27, 'PISTA DE BICICROSS', 1, 104),
    (28, 'PRAÇA DO ALTEROSA', 1, 34),
    (29, 'PRAÇA DO ENCONTRO', 2, 10),
    (30, 'REGIONAL ICAIVERA', 4, 65)
ON DUPLICATE KEY UPDATE
    nome = VALUES(nome), regional_codigo = VALUES(regional_codigo),
    matriculados = VALUES(matriculados), ativo = TRUE;

INSERT INTO modalidades (codigo, nome) VALUES
    (1, 'ATIVIDADE AQUÁTICA ADAPTADA'),
    (2, 'ATIVIDADE FÍSICA ADAPTADA - AFA'),
    (3, 'ATLETISMO OLÍMPICO'),
    (4, 'ATLETISMO PARAOLÍMPICO'),
    (5, 'BALLET'), (6, 'BASQUETEBOL'), (7, 'BEACH TÊNIS'),
    (8, 'DEFESA PESSOAL'), (9, 'ESPORTES'), (10, 'FUTEBOL DE CAMPO'),
    (11, 'FUTSAL'), (12, 'GINÁSTICA E ALONGAMENTO'),
    (13, 'GINÁSTICA PARA SAÚDE'), (14, 'GINÁSTICA RÍTMICA'),
    (15, 'HANDEBOL'), (16, 'HIDROGINÁSTICA'),
    (17, 'INICIAÇÃO ESPORTIVA'), (18, 'JUDÔ'), (19, 'NATAÇÃO'),
    (20, 'TAEKWONDO'), (21, 'VÔLEI DE PRAIA'), (22, 'VOLEIBOL'),
    (23, 'XADREZ'), (24, 'GINÁSTICA ARTÍSTICA'), (25, 'BICICROSS')
ON DUPLICATE KEY UPDATE nome = VALUES(nome), ativo = TRUE;

INSERT INTO nucleos_modalidades (modalidade_codigo, nucleo_codigo, matriculados) VALUES
    (1,20,104),(1,21,0),(2,20,26),(3,20,105),(3,10,0),(3,11,18),
    (4,20,39),(4,11,30),(4,10,0),(5,11,33),(6,11,0),(6,10,0),
    (6,20,114),(7,20,23),(7,10,0),(8,18,0),(9,22,186),(10,10,0),
    (10,2,97),(10,3,95),(10,4,45),(10,5,63),(10,6,130),(10,7,36),
    (10,8,114),(10,25,121),(10,11,0),(11,1,27),(11,14,21),(11,11,108),
    (11,20,474),(11,15,48),(11,24,74),(11,19,65),(11,10,0),(11,23,41),
    (24,1,0),(24,18,365),(12,23,1),(12,1,35),(12,20,268),(12,11,125),
    (13,30,26),(13,27,9),(13,1,61),(13,23,0),(13,11,257),(13,21,48),
    (13,24,19),(13,20,207),(13,10,0),(13,28,34),(13,29,10),(14,11,0),
    (14,18,29),(15,11,0),(15,10,64),(15,13,0),(15,20,70),(16,26,55),
    (16,10,0),(17,9,50),(17,1,0),(17,20,173),(17,11,33),(18,11,159),
    (18,24,17),(18,18,78),(18,12,33),(18,16,10),(18,10,0),(18,20,63),
    (18,21,17),(19,26,116),(19,10,0),(20,17,42),(20,23,177),(20,18,90),
    (20,1,35),(20,20,1),(20,11,33),(20,30,39),(20,10,0),(21,10,0),
    (21,20,88),(22,20,481),(22,11,153),(22,13,47),(22,14,11),(22,15,57),
    (22,23,45),(22,10,0),(23,22,63),(24,1,78),(25,27,95)
ON DUPLICATE KEY UPDATE matriculados = VALUES(matriculados);

-- Conferências úteis após a carga:
-- SELECT COUNT(*) AS regionais FROM regionais;       -- 11
-- SELECT COUNT(*) AS nucleos FROM nucleos;           -- 30
-- SELECT COUNT(*) AS modalidades FROM modalidades;   -- 25
-- SELECT COUNT(*) AS ofertas FROM nucleos_modalidades; -- 95
