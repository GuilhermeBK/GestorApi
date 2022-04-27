-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: gestorapi
-- ------------------------------------------------------
-- Server version	5.7.37-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'LAZER'),(2,'ALIMENTACAO'),(3,'MERCADO'),(4,'OUTROS');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'01','criar e registrar categorias','SQL','V01__criar_e_registrar_categorias.sql',1106692120,'root','2022-03-24 19:45:41',38,1),(2,'02','criar cadastros pessoas','SQL','V02__criar_cadastros_pessoas.sql',-407964074,'root','2022-03-24 19:45:41',45,1),(3,'03','criar tabela e registrar lancamentos','SQL','V03__criar_tabela_e_registrar_lancamentos.sql',-186072702,'root','2022-03-24 19:45:42',26,1),(4,'04','criar e registrar usuarios e permissoes','SQL','V04__criar_e_registrar_usuarios_e_permissoes.sql',984273638,'root','2022-03-24 19:45:42',140,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lancamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  `data_vencimento` date NOT NULL,
  `data_pagamento` date DEFAULT NULL,
  `valor` decimal(10,2) NOT NULL,
  `observacao` varchar(100) DEFAULT NULL,
  `tipo` varchar(20) NOT NULL,
  `codigo_categoria` bigint(20) NOT NULL,
  `codigo_pessoa` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_categoria` (`codigo_categoria`),
  KEY `codigo_pessoa` (`codigo_pessoa`),
  CONSTRAINT `lancamento_ibfk_1` FOREIGN KEY (`codigo_categoria`) REFERENCES `categoria` (`codigo`),
  CONSTRAINT `lancamento_ibfk_2` FOREIGN KEY (`codigo_pessoa`) REFERENCES `pessoa` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
INSERT INTO `lancamento` VALUES (1,'atualizando','2022-12-12',NULL,1000.00,NULL,'RECEITA',2,2),(2,'salario','2022-12-12',NULL,1000.00,NULL,'RECEITA',1,1),(3,'Salário mensal','2018-01-27',NULL,6500.00,'Distribuição de lucros','RECEITA',1,1),(4,'Supermercado','2018-03-10','2018-03-01',100.32,NULL,'DESPESA',2,2),(5,'Academia','2018-04-10',NULL,120.00,NULL,'DESPESA',3,3),(6,'Conta de luz','2018-02-10','2018-02-10',110.44,NULL,'DESPESA',3,4),(7,'Conta de água','2018-02-15',NULL,200.30,NULL,'DESPESA',3,5),(8,'Restaurante','2018-03-14','2018-03-14',1010.32,NULL,'DESPESA',4,6),(9,'Venda vídeo game','2018-01-01',NULL,500.00,NULL,'RECEITA',1,7),(10,'Clube','2018-03-07','2018-03-05',400.32,NULL,'DESPESA',4,8),(11,'Impostos','2018-04-10',NULL,123.64,'Multas','DESPESA',3,9),(13,'Padaria','2018-02-28','2018-02-28',8.32,NULL,'DESPESA',1,5),(15,'Almoço','2018-03-09',NULL,1040.32,NULL,'DESPESA',4,3),(16,'Café','2018-02-20','2018-02-18',4.32,NULL,'DESPESA',4,2),(17,'Lanche','2018-04-10',NULL,10.20,NULL,'DESPESA',4,1),(18,'salario','2022-12-12',NULL,1000.00,NULL,'RECEITA',1,1),(19,'salario','2022-03-12',NULL,1000.00,NULL,'RECEITA',1,1),(20,'CAIXA','2022-03-12',NULL,1000.00,NULL,'DESPESA',1,1),(21,'CAIXA','2022-03-12',NULL,1000.00,NULL,'DESPESA',1,1),(22,'CAIXA','2022-03-12',NULL,1000.00,NULL,'DESPESA',2,1);
/*!40000 ALTER TABLE `lancamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissao` (
  `codigo` bigint(20) NOT NULL,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao`
--

LOCK TABLES `permissao` WRITE;
/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` VALUES (1,'ROLE_CADASTRAR_CATEGORIA'),(2,'ROLE_PESQUISAR_CATEGORIA'),(3,'ROLE_CADASTRAR_PESSOA'),(4,'ROLE_REMOVER_PESSOA'),(5,'ROLE_PESQUISAR_PESSOA'),(6,'ROLE_CADASTRAR_LANCAMENTO'),(7,'ROLE_REMOVER_LANCAMENTO'),(8,'ROLE_PESQUISAR_LANCAMENTO');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `logradouro` varchar(30) DEFAULT NULL,
  `numero` varchar(30) DEFAULT NULL,
  `complemento` varchar(30) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `cep` varchar(30) DEFAULT NULL,
  `cidade` varchar(30) DEFAULT NULL,
  `estado` varchar(30) DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'João Silva','Rua do Abacaxi','10',NULL,'Brasil','38.400-121','Uberlândia','MG',1),(2,'Maria Rita','Rua do Sabiá','110','Apto 101','Colina','11.400-121','Ribeirão Preto','SP',1),(3,'Pedro Santos','Rua da Bateria','23',NULL,'Morumbi','54.212-121','Goiânia','GO',1),(4,'Ricardo Pereira','Rua do Motorista','123','Apto 302','Aparecida','38.400-12','Salvador','BA',1),(5,'Josué Mariano','Av Rio Branco','321',NULL,'Jardins','56.400-121','Natal','RN',1),(6,'Pedro Barbosa','Av Brasil','100',NULL,'Tubalina','77.400-121','Porto Alegre','RS',1),(7,'Henrique Medeiros','Rua do Sapo','1120','Apto 201','Centro','12.400-121','Rio de Janeiro','RJ',1),(8,'Carlos Santana','Rua da Manga','433',NULL,'Centro','31.400-121','Belo Horizonte','MG',1),(9,'Leonardo Oliveira','Rua do Músico','566',NULL,'Segismundo Pereira','38.400-00','Uberlândia','MG',1),(10,'Isabela Martins','Rua da Terra','1233','Apto 10','Vigilato','99.400-121','Manaus','AM',1),(11,'guilherme','Rua sapiranga','556',NULL,'sao luiz','93806332','sao paulo','RS',0);
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(150) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador','adm@gmail.com','$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.'),(2,'Maria Silva','maria@gmail.com','$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_permissao`
--

DROP TABLE IF EXISTS `usuario_permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_permissao` (
  `codigo_usuario` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo_usuario`,`codigo_permissao`),
  KEY `codigo_permissao` (`codigo_permissao`),
  CONSTRAINT `usuario_permissao_ibfk_1` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuario` (`codigo`),
  CONSTRAINT `usuario_permissao_ibfk_2` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_permissao`
--

LOCK TABLES `usuario_permissao` WRITE;
/*!40000 ALTER TABLE `usuario_permissao` DISABLE KEYS */;
INSERT INTO `usuario_permissao` VALUES (1,1),(1,2),(2,2),(1,3),(1,4),(1,5),(2,5),(1,6),(1,7),(1,8),(2,8);
/*!40000 ALTER TABLE `usuario_permissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gestorapi'
--

--
-- Dumping routines for database 'gestorapi'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-26 19:14:47
