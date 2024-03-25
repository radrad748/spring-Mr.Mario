-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mr_mario
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `description` varchar(400) DEFAULT '',
  `name` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `type_menu` int DEFAULT NULL,
  `restaurant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKblwdtxevpl4mrds8a12q0ohu6` (`restaurant_id`),
  CONSTRAINT `FKblwdtxevpl4mrds8a12q0ohu6` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2024-03-20 10:00:00.000000','Ветчина, копченые колбаски, овощи - 210гр.','Омлет',1.50,0,1),(2,'2024-03-20 10:00:00.000000','250гр.','Рисовая каша',0.70,0,1),(3,'2024-03-20 10:00:00.000000','с тунцом и яйцлм пашот 270гр.','Нисуаз',2.70,1,1),(4,'2024-03-20 10:00:00.000000','с можениной 290гр.','Мясной салат',2.30,1,1),(5,'2024-03-20 10:00:00.000000','Томатный соус, сыр моцарелла, орегано 490гр.','Маргарита',1.90,2,1),(6,'2024-03-20 10:00:00.000000','Томатный соус, сыр моцарелла, салями, острый перец 470гр.','Виавола',2.00,2,1),(7,'2024-03-20 10:00:00.000000','Куриные потрошка тушеные в белом вине с пряными травами 300гр.','Кучмачи',2.10,3,1),(8,'2024-03-20 10:00:00.000000','Курица тушеная в собственом соку с томатами, яйцом, специями и зеленью 300гр.','Чахохбили',2.00,3,1),(9,'2024-03-20 10:00:00.000000','Красное полусладкое 11%','Carranc',6.90,4,1),(10,'2024-03-20 10:00:00.000000','Красное сухое 21%','Frontera, Concha y Toro, Cabernet Sauvignon',6.50,4,1),(11,'2024-03-20 10:00:00.000000','Кофейный ликер 15мл, айриш крим 15мл, ликер трипл сек 15мл','B-52',3.50,5,1),(12,'2024-03-20 10:00:00.000000','Самбука 10мл, серебряная текила 10мл, абсент 10мл, ликюр блю курасао 10мл, айриш крим 10мл','Облака',4.50,5,1),(13,'2024-03-20 10:00:00.000000','','Jameson',10.00,6,1),(14,'2024-03-20 10:00:00.000000','','Jack Daniels',10.00,6,1),(15,'2024-03-20 10:00:00.000000','Молоко 400мл, мороженое пломбир 200г, взбитые сливки 50г','Молочный',1.50,7,1),(16,'2024-03-20 10:00:00.000000','Молоко 150мл, мягкое мороженое 100г, топпинг Icedream фруктовый или ягодный 10гр, взбитые сливки 40г, мята, шоколадная крошка','Icedream',1.50,7,1),(17,'2024-03-20 10:00:00.000000','','Зеленый с жасмином',1.00,8,1),(18,'2024-03-20 10:00:00.000000','','Чай с лесными ягодами',1.00,8,1),(19,'2024-03-20 10:00:00.000000','','Американо',1.70,9,1),(20,'2024-03-20 10:00:00.000000','','Капучино',2.00,9,1),(21,'2024-03-20 10:00:00.000000','','Байкалская',1.50,10,1),(22,'2024-03-20 10:00:00.000000','','Evian',0.50,10,1),(23,'2024-03-20 10:00:00.000000','','Ванильное мороженое-пломбир',1.50,11,1),(24,'2024-03-20 10:00:00.000000','','Банановое мороженое',1.80,11,1),(25,'2024-03-20 10:00:00.000000','Горкий шоколад, белый шоколад, сливки, сахарная пудра','Буше Бисквитное',2.00,12,1),(26,'2024-03-20 10:00:00.000000','Какао, молоко, коньяк, темный шоколад, арахис, кокосовая струшка, вишневое варенье','Пироженое Птифуры',2.70,12,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-25 14:05:08
