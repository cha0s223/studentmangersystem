-- MySQL dump 10.13  Distrib 8.2.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: student_card_manage_system
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card` (
  `card_no` varchar(9) NOT NULL,
  `card_password` varchar(20) NOT NULL,
  `balance` float DEFAULT '0',
  `card_state` enum('0','1') DEFAULT '0' COMMENT '0是正常，1是被冻结',
  PRIMARY KEY (`card_no`),
  CONSTRAINT `card_chk_1` CHECK ((`balance` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='校园卡表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES ('advent','myyj259',25.9,'0'),('aminuosi','sxc258',525,'0'),('mambaout','seeyouagain',2,'0'),('ruike5dai','1?5!',299.9,'0'),('zhangsan','123456',100,'0');
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日志序号',
  `oper_id` varchar(30) DEFAULT '' COMMENT '操作员id',
  `oper_role` int DEFAULT NULL COMMENT '操作员权限',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `class_name` varchar(100) DEFAULT '' COMMENT '请求类名',
  `request_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `request_url` varchar(255) DEFAULT '' COMMENT '请求url',
  `status` int DEFAULT NULL COMMENT '0是正常，1是错误',
  `error_msg` varchar(2000) DEFAULT NULL,
  `response_result` varchar(2000) DEFAULT '' COMMENT '响应数据',
  `oper_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `take_time` bigint DEFAULT NULL COMMENT '方法执行耗时（单位：毫秒）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (1,'12345678911',1,'addCard','com.work.studentmangersystem.controller.OperatorController','{\"balance\":100.0,\"cardNo\":\"zhangsan\",\"cardPassword\":\"123456\",\"cardState\":\"0\"}','POST','/operator/add',0,NULL,'{\"code\":0,\"data\":\"添加成功!\",\"msg\":\"success\",\"timestamp\":1718958234455}','2024-06-21 08:23:54',11),(2,'12345678911',1,'addCard','com.work.studentmangersystem.controller.OperatorController','{\"balance\":100.0,\"cardNo\":\"zhangsan\",\"cardPassword\":\"123456\",\"cardState\":\"0\"}','POST','/operator/add',1,'org.springframework.dao.DuplicateKeyException:\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'zhangsan\' for key \'card.PRIMARY\'\r\n### The error may exist in com/work/studentmangersystem/mapper/CardMapper.java (best guess)\r\n### The error may involve com.work.studentmangersystem.mapper.CardMapper.insert\r\n### The error occurred while executing an update\r\n### SQL: INSERT INTO `card`(`card_no`, `card_password`, `balance`, `card_state`) VALUES (?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'zhangsan\' for key \'card.PRIMARY\'\n; Duplicate entry \'zhangsan\' for key \'card.PRIMARY\'\n	org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:254)\norg.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:107)\norg.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:92)\norg.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:439)\njdk.proxy2/jdk.proxy2.$Proxy75.insert(Unknown Source)\norg.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:272)\norg.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:62)\norg.apache.ibatis.binding.MapperProxy$PlainMethodInvoker.invoke(MapperProxy.java:141)\norg.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:86)\njdk.proxy2/jdk.proxy2.$Proxy84.insert(Unknown Source)\ncom.mybatisflex.core.BaseMapper.insert(BaseMapper.java:87)\njava.base/java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:733)\norg.apache.ibatis.binding.MapperProxy$DefaultMethodInvoker.invoke(MapperProxy.java:154)\norg.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:86)\njdk.proxy2/jdk.proxy2.$Proxy84.insert(Unknown Source)\njava.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)\njava',NULL,'2024-06-21 08:25:40',NULL);
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `cno` varchar(9) NOT NULL,
  `t_time` timestamp NOT NULL,
  `c_thing` varchar(20) DEFAULT NULL,
  `amount` float NOT NULL,
  PRIMARY KEY (`cno`,`t_time`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`cno`) REFERENCES `card` (`card_no`),
  CONSTRAINT `transaction_chk_1` CHECK ((`amount` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='交易记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES ('advent','2024-04-14 06:47:44','2/5/9',2.59),('aminuosi','2024-02-05 06:46:10','porridge',1200),('aminuosi','2024-06-19 05:34:46','充值',500),('ruike5dai','2024-05-12 05:47:47','ruike2',199.9),('ruike5dai','2024-06-12 05:47:22','ruike',99.9);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `mobile` varchar(11) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` enum('0','1','2') DEFAULT '0' COMMENT '0是卡用户，1是卡业务员，2是系统管理员',
  `cno` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`mobile`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `cno` (`cno`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`cno`) REFERENCES `card` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('12345678910','123456789','123456','2',NULL),('12345678911','LTC丁真','igotsmoke','1','ruike5dai'),('12345678912','孙笑川258','aminuosi','0','aminuosi'),('12345678913','advent','259259','0','advent'),('12345678914','kobe','man!wcic','0','mambaout'),('admin','admin','123456','2',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-21 16:39:10
