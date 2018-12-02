SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema prestacao_contas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema prestacao_contas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `prestacao_contas` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema prestacao_contas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema prestacao_contas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `prestacao_contas` DEFAULT CHARACTER SET utf8 ;
USE `prestacao_contas` ;

-- -----------------------------------------------------
-- Table `prestacao_contas`.`tipo_prestacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`tipo_prestacao` (
  `id_tipo_prestacao` INT NOT NULL AUTO_INCREMENT,
  `tipo_prestacao` VARCHAR(45) NULL,
  PRIMARY KEY (`id_tipo_prestacao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prestacao_contas`.`tipo_aprovacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`tipo_aprovacao` (
  `id_tipo_aprovacao` INT NOT NULL AUTO_INCREMENT,
  `tipo_aprovacao` VARCHAR(45) NULL,
  PRIMARY KEY (`id_tipo_aprovacao`))
ENGINE = InnoDB;

USE `prestacao_contas` ;

-- -----------------------------------------------------
-- Table `prestacao_contas`.`tipo_funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`tipo_funcionario` (
  `id_tipo_funcionario` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo_funcionario` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_tipo_funcionario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prestacao_contas`.`funcionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`funcionarios` (
  `id_funcionario` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `cpf` CHAR(11) NULL DEFAULT NULL,
  `id_tipo_funcionario` INT(11) NOT NULL,
  `limite_aprovacao` FLOAT NULL DEFAULT NULL,
  `senha` VARCHAR(45) NULL DEFAULT NULL,
  `id_chefe` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`),
  INDEX `fk_Funcionario_Tipo_Funcionario1_idx` (`id_tipo_funcionario` ASC) ,
  INDEX `fk_funcionario_funcionario1_idx` (`id_chefe` ASC) ,
  CONSTRAINT `fk_Funcionario_Tipo_Funcionario1`
    FOREIGN KEY (`id_tipo_funcionario`)
    REFERENCES `prestacao_contas`.`tipo_funcionario` (`id_tipo_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funcionario_funcionario1`
    FOREIGN KEY (`id_chefe`)
    REFERENCES `prestacao_contas`.`funcionarios` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prestacao_contas`.`cartoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`cartoes` (
  `id_cartao` INT(16) NOT NULL AUTO_INCREMENT,
  `validade` CHAR(5) NULL DEFAULT NULL,
  `nome_impresso` VARCHAR(45) NULL DEFAULT NULL,
  `cod_seguranca` CHAR(3) NULL DEFAULT NULL,
  `id_funcionario` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id_cartao`),
  INDEX `fk_Cartao_Funcionario1_idx` (`id_funcionario` ASC) ,
  CONSTRAINT `fk_Cartao_Funcionario1`
    FOREIGN KEY (`id_funcionario`)
    REFERENCES `prestacao_contas`.`funcionarios` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prestacao_contas`.`tipo_despesa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`tipo_despesa` (
  `id_tipo_despesa` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo_despesa` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_tipo_despesa`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prestacao_contas`.`prestacao_contas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prestacao_contas`.`prestacao_contas` (
  `id_prestacao_conta` INT NOT NULL AUTO_INCREMENT,
  `id_funcionario` INT(11) NOT NULL,
  `id_tipo_prestacao` INT NOT NULL,
  `id_cartao` INT(16),
  `id_tipo_despesa` INT(11) NOT NULL,
  `id_tipo_aprovacao` INT NULL,
  `valor` DOUBLE NULL,
  `data` DATE NULL,
  `descricao` TEXT NULL,
  PRIMARY KEY (`id_prestacao_conta`),
  INDEX `fk_funcionario_has_despesas_funcionario1_idx` (`id_funcionario` ASC) ,
  INDEX `fk_prestacao_contas_tipo_prestacao1_idx` (`id_tipo_prestacao` ASC) ,
  INDEX `fk_prestacao_contas_tipo_despesa1_idx` (`id_tipo_despesa` ASC) ,
  INDEX `fk_prestacao_contas_cartoes1_idx` (`id_cartao` ASC) ,
  INDEX `fk_prestacao_contas_tipo_aprovacao1_idx` (`id_tipo_aprovacao` ASC) ,
  CONSTRAINT `fk_funcionario_has_despesas_funcionario1`
    FOREIGN KEY (`id_funcionario`)
    REFERENCES `prestacao_contas`.`funcionarios` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestacao_contas_tipo_prestacao1`
    FOREIGN KEY (`id_tipo_prestacao`)
    REFERENCES `prestacao_contas`.`tipo_prestacao` (`id_tipo_prestacao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestacao_contas_tipo_despesa1`
    FOREIGN KEY (`id_tipo_despesa`)
    REFERENCES `prestacao_contas`.`tipo_despesa` (`id_tipo_despesa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestacao_contas_cartoes1`
    FOREIGN KEY (`id_cartao`)
    REFERENCES `prestacao_contas`.`cartoes` (`id_cartao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestacao_contas_tipo_aprovacao1`
    FOREIGN KEY (`id_tipo_aprovacao`)
    REFERENCES `prestacao_contas`.`tipo_aprovacao` (`id_tipo_aprovacao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into tipo_funcionario(tipo_funcionario) values("SUPER Chefe");
insert into tipo_funcionario(tipo_funcionario) values("Chefe");
insert into tipo_funcionario(tipo_funcionario) values("Funcionario Comum");


insert into tipo_despesa(tipo_despesa) values("Transporte");
insert into tipo_despesa(tipo_despesa) values("Alimentacao");
insert into tipo_despesa(tipo_despesa) values("Luz");
insert into tipo_despesa(tipo_despesa) values("Agua");


insert into tipo_aprovacao(tipo_aprovacao) values("Aprovado");
insert into tipo_aprovacao(tipo_aprovacao) values("Em analize");
insert into tipo_aprovacao(tipo_aprovacao) values("Negado");


insert into tipo_prestacao(tipo_prestacao) values("Prestacao Normal");
insert into tipo_prestacao(tipo_prestacao) values("Reembolso");


insert into funcionarios(nome,cpf,id_tipo_funcionario,limite_aprovacao,senha) values("Filipe Dru","13162335612",'1','999999',"123qwe!@#");
insert into funcionarios(nome,cpf,id_tipo_funcionario,limite_aprovacao,senha,id_chefe) values("Medllyn Ta","13162335612",'1','2500',"123qwe!@#",'1');


insert into cartoes(validade,nome_impresso,cod_seguranca,id_funcionario) values("05/21","Filipe Dru","555",'1');
insert into cartoes(validade,nome_impresso,cod_seguranca,id_funcionario) values("05/21","Medllyn Ta","555",'2');

insert into prestacao_contas(id_funcionario,id_tipo_prestacao,id_cartao,id_tipo_despesa,id_tipo_aprovacao,valor,data,descricao)values('1','1','1','1','1','22.10','2018-11-27',"uber ate o trabalho");
insert into prestacao_contas(id_funcionario,id_tipo_prestacao,id_tipo_despesa,id_tipo_aprovacao,valor,data,descricao)values('2','2','3','2','55.10','2018-12-01',"conta de luz agosto");
insert into prestacao_contas(id_funcionario,id_tipo_prestacao,id_tipo_despesa,id_tipo_aprovacao,valor,data,descricao)values('2','2','3','1','55.10','2018-12-01',"Almoço");




