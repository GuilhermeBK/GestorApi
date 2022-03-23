CREATE TABLE categoria (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    INSERT INTO categoria (nome) VALUES ('LAZER');
    INSERT INTO categoria (nome) VALUES ('ALIMENTACAO');
    INSERT INTO categoria (nome) VALUES ('MERCADO');
    INSERT INTO categoria (nome) VALUES ('OUTROS');