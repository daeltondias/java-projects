-- Table Pessoa
-- -----------------------------------------------------
CREATE TABLE pessoa (
  id BIGSERIAL NOT NULL,
  nome VARCHAR(100) NOT NULL,
  datanascimento DATE,
  cpf VARCHAR(14),
  funcionario BOOLEAN,
  gerente BOOLEAN,
  CONSTRAINT pk_pessoa PRIMARY KEY (id)
);
-- -----------------------------------------------------
-- Table Projeto
-- -----------------------------------------------------
CREATE TABLE projeto (
  id BIGSERIAL NOT NULL,
  nome VARCHAR(200) NOT NULL,
  data_inicio DATE,
  data_previsao_fim DATE,
  data_fim DATE,
  descricao VARCHAR(5000),
  status VARCHAR(45),
  orcamento FLOAT,
  risco VARCHAR(45),
  idgerente BIGINT NOT NULL,
  CONSTRAINT pk_projeto PRIMARY KEY (id),
  CONSTRAINT fk_gerente FOREIGN KEY (idgerente) REFERENCES pessoa (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
-- -----------------------------------------------------
-- Table Membro Projeto
-- -----------------------------------------------------
CREATE TABLE membro_projeto (
  id BIGSERIAL PRIMARY KEY,
  idpessoa BIGINT NOT NULL,
  idprojeto BIGINT NOT NULL,
  FOREIGN KEY (idpessoa) REFERENCES pessoa (id),
  FOREIGN KEY (idprojeto) REFERENCES projeto (id)
);
-- -----------------------------------------------------
-- Inserting data into the person table
-- -----------------------------------------------------
INSERT INTO pessoa (nome, datanascimento, cpf, funcionario, gerente)
VALUES (
    'Rita Martins',
    '1997-06-06',
    '858.785.215-98',
    true,
    false
  ),
  (
    'Carlos Souza',
    '1985-12-15',
    '123.456.789-00',
    true,
    true
  ),
  (
    'Juliana Rocha',
    '1990-03-22',
    '987.654.321-00',
    true,
    false
  ),
  (
    'Fernando Lima',
    '1978-09-10',
    '456.789.123-77',
    false,
    false
  ),
  (
    'Paula Mendes',
    '2000-01-05',
    '789.123.456-55',
    true,
    false
  ),
  (
    'Marcos Silva',
    '1982-07-18',
    '321.654.987-11',
    false,
    false
  ),
  (
    'Tatiane Oliveira',
    '1995-11-30',
    '741.852.963-33',
    true,
    true
  ),
  (
    'Ricardo Alves',
    '1993-05-25',
    '852.963.741-22',
    true,
    false
  ),
  (
    'Bianca Castro',
    '1988-10-12',
    '963.741.852-44',
    false,
    false
  ),
  (
    'Luiz Fernando',
    '1999-08-03',
    '159.357.258-66',
    true,
    false
  );