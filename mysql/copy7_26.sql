/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.25 : Database - guli
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`guli` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `guli`;

/*Table structure for table `crm_banner` */

DROP TABLE IF EXISTS `crm_banner`;

CREATE TABLE `crm_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页banner表';

/*Data for the table `crm_banner` */

insert  into `crm_banner`(`id`,`title`,`image_url`,`link_url`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1194556896025845762','test1','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg','/course',1,0,'2019-11-13 18:05:32','2019-11-18 10:28:22'),
('1194607458461216769','test2','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/13/8f80790d-d736-4842-a6a4-4dcb0d684d4e.jpg','/teacher',2,0,'2019-11-13 21:26:27','2019-11-14 09:12:15');

/*Table structure for table `edu_chapter` */

DROP TABLE IF EXISTS `edu_chapter`;

CREATE TABLE `edu_chapter` (
  `id` char(19) NOT NULL COMMENT '章节ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_chapter` */

insert  into `edu_chapter`(`id`,`course_id`,`title`,`sort`,`gmt_create`,`gmt_modified`) values 
('1','14','第一章：HTML',0,'2019-01-01 12:27:40','2019-01-01 12:55:30'),
('1181729226915577857','18','第七章：I/O流',70,'2019-10-09 08:32:58','2019-10-09 08:33:20'),
('1192252428399751169','1192252213659774977','第一章节',0,'2019-11-07 09:28:25','2019-11-07 09:28:25'),
('15','18','第一章：Java入门',0,'2019-01-01 12:27:40','2019-10-09 09:13:19'),
('3','14','第二章：CSS',0,'2019-01-01 12:55:35','2019-01-01 12:27:40'),
('32','18','第二章：控制台输入和输出',0,'2019-01-01 12:27:40','2019-01-01 12:27:40'),
('44','18','第三章：控制流',0,'2019-01-01 12:27:40','2019-01-01 12:27:40'),
('48','18','第四章：类的定义',0,'2019-01-01 12:27:40','2019-01-01 12:27:40'),
('63','18','第五章：数组',0,'2019-01-01 12:27:40','2019-01-01 12:27:40'),
('64','18','第六章：继承',61,'2019-01-01 12:27:40','2019-10-09 08:32:47');

/*Table structure for table `edu_comment` */

DROP TABLE IF EXISTS `edu_comment`;

CREATE TABLE `edu_comment` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `teacher_id` char(19) NOT NULL DEFAULT '' COMMENT '讲师id',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论';

/*Data for the table `edu_comment` */

insert  into `edu_comment`(`id`,`course_id`,`teacher_id`,`member_id`,`nickname`,`avatar`,`content`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1194499162790211585','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','课程很好',0,'2019-11-13 14:16:08','2019-11-13 14:16:08'),
('1194898406466420738','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-14 16:42:35','2019-11-14 16:42:35'),
('1194898484388200450','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','111',0,'2019-11-14 16:42:53','2019-11-14 16:42:53'),
('1195251020861317122','1192252213659774977','1189389726308478977','1',NULL,NULL,'2233',0,'2019-11-15 16:03:45','2019-11-15 16:03:45'),
('1195251382720700418','1192252213659774977','1189389726308478977','1',NULL,NULL,'4455',0,'2019-11-15 16:05:11','2019-11-15 16:05:11'),
('1195252819177570306','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:10:53','2019-11-15 16:10:53'),
('1195252899448160258','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:11:13','2019-11-15 16:11:13'),
('1195252920587452417','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','223344',0,'2019-11-15 16:11:18','2019-11-15 16:11:18'),
('1195262128095559681','14','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-15 16:47:53','2019-11-15 16:47:53'),
('1196264505170767874','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','666666',0,'2019-11-18 11:10:58','2019-11-18 11:10:58');

/*Table structure for table `edu_course` */

DROP TABLE IF EXISTS `edu_course`;

CREATE TABLE `edu_course` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) NOT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_course` */

insert  into `edu_course`(`id`,`teacher_id`,`subject_id`,`subject_parent_id`,`title`,`price`,`lesson_num`,`cover`,`buy_count`,`view_count`,`version`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1192252213659774977','1189389726308478977','1178214681139539969','1178214681118568449','java基础课程：test',0.01,2,'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/cover/default.gif',4,407,1,'Normal',0,'2019-11-07 09:27:33','2021-07-23 14:48:32'),
('14','1189389726308478977','1101348944971091969','1101348944920760321','XHTML CSS2 JS整站制作教程课程学习',0.00,3,'http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/13/d0086eb0-f2dc-45f7-bba1-744d95e5be0f.jpg',3,52,15,'Normal',0,'2018-04-02 18:33:34','2021-07-23 13:52:55'),
('1417038758282137601','1189389726308478977','1178585108407984130','1178214681118568449','测试2',1.00,1,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/656b9d34-3172-4737-8bec-e9dbb51362c300c37e754f9076fd31028bdfb6021bad.jpg',1,2,1,'Draft',0,'2021-07-19 16:29:02','2021-07-23 10:17:51'),
('1417038937391501314','1','1178214681210843137','1178214681181483010','测试1',0.00,2,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/cf72f3d5-bee9-4b55-84c0-a4160224ed261c6a321a6a82acca960df64f73b3cb5c.jpg',50,4,1,'Draft',0,'2021-07-19 16:29:45','2021-07-23 10:17:55'),
('1417039036905558018','1','1178585108407984130','1178214681118568449','测试3',2.00,2,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/4eab428f-b7e3-4ef1-a5fb-3666839acbaf28fc134eeae9b1cb0a68a1922a7fcdd7.jpg',10,3,1,'Draft',0,'2021-07-19 16:30:08','2021-07-23 10:17:59'),
('1417039213141823489','1189390295668469762','1178214681294729217','1178214681231814658','测试4',2.00,1,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/dc147ee8-8ef4-43bf-b1a2-a07a8d55fd735d6113baa2f2215ea7b0eb24e8718345.jpg',55,2,1,'Draft',0,'2021-07-19 16:30:50','2021-07-23 10:18:04'),
('1417039299104083969','1189426464967995393','1178214681454112770','1178214681399586817','测试5',3.00,2,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/287a3367-36dc-44ba-a079-31b37492fa8fa3fdb790ecfcdd42341de5e6db79298f.jpg',11,1,1,'Draft',0,'2021-07-19 16:31:11','2021-07-22 19:26:53'),
('1417039385963925506','1189389726308478977','1178214681529610242','1178214681483472898','测试6',4.00,2,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/a4e910ab-6b8c-4999-b96e-f60e6ee5182fb9dc6cc4bcfd22b818e28312a0cdf415.jpg',55,1,1,'Draft',0,'2021-07-19 16:31:32','2021-07-22 19:04:23'),
('1417039474925113346','1189426464967995393','1178214681584136193','1178214681554776066','测试7',2.00,3,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/2639aefa-e6f0-4bd6-83b2-24dfb60e0cbcebbe1d4a98718f32f83909dc55e6dadc.jpg',66,3,1,'Draft',0,'2021-07-19 16:31:53','2021-07-22 19:35:27'),
('1417039564209262593','1189389726308478977','1178214681626079234','1178214681613496321','测试8',0.00,2,'https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/2021/07/19/2322f195-c89c-4aec-b51b-daa4f8e2ecbaf28a6c378c8d8807269b0bf773d40752.jpg',7,4,1,'Draft',0,'2021-07-19 16:32:14','2021-07-22 19:26:21'),
('18','1189389726308478977','1178214681139539969','1178214681118568449','Java精品课程',0.01,20,'http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/06/866e9aca-b530-4f71-a690-72d4a4bfd1e7.jpg',151,746,6,'Normal',0,'2018-04-02 21:28:46','2021-07-23 10:55:37'),
('50','1189389726308478977','1101348944971091969','1101348944920760321','HTML5入门课程学习',0.00,23,'http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/13/22997b8e-3606-4d2e-9b4f-09f48418b6e4.jpg',8,51,17,'Normal',0,'2018-04-02 18:34:32','2019-11-12 10:19:20');

/*Table structure for table `edu_course_collect` */

DROP TABLE IF EXISTS `edu_course_collect`;

CREATE TABLE `edu_course_collect` (
  `id` char(19) NOT NULL COMMENT '收藏ID',
  `course_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程收藏';

/*Data for the table `edu_course_collect` */

insert  into `edu_course_collect`(`id`,`course_id`,`member_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1196269345666019330','1192252213659774977','1',1,'2019-11-18 11:30:12','2019-11-18 11:30:12');

/*Table structure for table `edu_course_description` */

DROP TABLE IF EXISTS `edu_course_description`;

CREATE TABLE `edu_course_description` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `description` text COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程简介';

/*Data for the table `edu_course_description` */

insert  into `edu_course_description`(`id`,`description`,`gmt_create`,`gmt_modified`) values 
('1104870479077879809','<p>11</p>','2019-03-11 06:23:44','2019-03-11 06:23:44'),
('1192252213659774977','<p>测试</p>','2019-11-07 09:27:33','2019-11-13 16:21:28'),
('14','','2019-03-13 06:04:43','2019-03-13 06:05:33'),
('1417038758282137601','','2021-07-19 16:29:02','2021-07-19 16:29:02'),
('1417038937391501314','<p>&nbsp;&nbsp;&nbsp;</p>','2021-07-19 16:29:45','2021-07-19 16:29:45'),
('1417039036905558018','','2021-07-19 16:30:08','2021-07-19 16:30:08'),
('1417039213141823489','','2021-07-19 16:30:50','2021-07-19 16:30:50'),
('1417039299104083969','','2021-07-19 16:31:11','2021-07-19 16:31:11'),
('1417039385963925506','','2021-07-19 16:31:32','2021-07-19 16:31:32'),
('1417039474925113346','','2021-07-19 16:31:53','2021-07-19 16:31:53'),
('1417039564209262593','','2021-07-19 16:32:14','2021-07-19 16:32:14'),
('15','','2019-03-13 06:03:33','2019-03-13 06:04:22'),
('18','<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>','2019-03-06 18:06:36','2019-10-30 19:58:36');

/*Table structure for table `edu_login` */

DROP TABLE IF EXISTS `edu_login`;

CREATE TABLE `edu_login` (
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` char(19) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

/*Data for the table `edu_login` */

insert  into `edu_login`(`code`,`id`,`name`,`mobile`,`password`,`email`) values 
(NULL,'1','admin','123456789','123456','123456@qq.com'),
('123','1418890091025027074','neo',NULL,'123456','777@qq.com');

/*Table structure for table `edu_subject` */

DROP TABLE IF EXISTS `edu_subject`;

CREATE TABLE `edu_subject` (
  `id` char(19) NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) NOT NULL COMMENT '类别名称',
  `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程科目';

/*Data for the table `edu_subject` */

insert  into `edu_subject`(`id`,`title`,`parent_id`,`sort`,`gmt_create`,`gmt_modified`) values 
('1178214681118568449','后端开发','0',1,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681139539969','Java','1178214681118568449',1,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681181483010','前端开发','0',3,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681210843137','JavaScript','1178214681181483010',4,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681231814658','云计算','0',5,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681252786178','Docker','1178214681231814658',5,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681294729217','Linux','1178214681231814658',6,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681324089345','系统/运维','0',7,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681353449473','Linux','1178214681324089345',7,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681382809602','Windows','1178214681324089345',8,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681399586817','数据库','0',9,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681428946945','MySQL','1178214681399586817',9,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681454112770','MongoDB','1178214681399586817',10,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681483472898','大数据','0',11,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681504444418','Hadoop','1178214681483472898',11,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681529610242','Spark','1178214681483472898',12,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681554776066','人工智能','0',13,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681584136193','Python','1178214681554776066',13,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681613496321','编程语言','0',14,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178214681626079234','Java','1178214681613496321',14,'2019-09-29 15:47:25','2019-09-29 15:47:25'),
('1178585108407984130','Python','1178214681118568449',2,'2019-09-30 16:19:22','2019-09-30 16:19:22'),
('1178585108454121473','HTML/CSS','1178214681181483010',3,'2019-09-30 16:19:22','2019-09-30 16:19:22');

/*Table structure for table `edu_teacher` */

DROP TABLE IF EXISTS `edu_teacher`;

CREATE TABLE `edu_teacher` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲师';

/*Data for the table `edu_teacher` */

insert  into `edu_teacher`(`id`,`name`,`intro`,`career`,`level`,`avatar`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1','张三','近年主持国家自然科学基金（6项）、江苏省重大科技成果转化项目（5项）、江苏省产学研前瞻性联合研究项目（3项）、省工业科技支撑、省高技术、省自然科学基金等省部级及其企业的主要科研项目40多个，多个项目在企业成功转化，产生了较好的经济、社会和环境效益。积极开展产学研科技合作，并与省内16家企业建立了江苏省研究生工作站，其中6家为江苏省优秀研究生工作站','高级',1,'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg',0,0,'2019-10-30 14:18:46','2019-11-12 13:36:36'),
('1189389726308478977','晴天','高级讲师简介','高级讲师资历',2,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/de47ee9b-7fec-43c5-8173-13c5f7f689b2.png',1,0,'2019-10-30 11:53:03','2019-10-30 11:53:03'),
('1189390295668469762','李刚','高级讲师简介','高级讲师',2,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/b8aa36a2-db50-4eca-a6e3-cc6e608355e0.png',2,0,'2019-10-30 11:55:19','2019-11-12 13:37:52'),
('1189426437876985857','王二','高级讲师简介','高级讲师',1,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/08/e44a2e92-2421-4ea3-bb49-46f2ec96ef88.png',3,0,'2019-10-30 14:18:56','2019-11-12 13:37:35'),
('1189426464967995393','王五','高级讲师简介','高级讲师',1,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png',4,0,'2019-10-30 14:19:02','2019-11-12 13:37:18'),
('1192249914833055746','李四','高级讲师简介','高级讲师',1,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/07/91871e25-fd83-4af6-845f-ea8d471d825d.png',5,0,'2019-11-07 09:18:25','2019-11-12 13:37:01'),
('1192327476087115778','1222-12-12','1111','11',1,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/08/5805c6cd-c8ad-4a77-aafd-d2e083bfd8a4.png',6,1,'2019-11-07 14:26:37','2019-11-11 16:26:26'),
('1195337453429129218','test','sdfsdf','sdfdf',1,'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg',7,1,'2019-11-15 21:47:12','2019-11-15 21:47:27');

/*Table structure for table `edu_video` */

DROP TABLE IF EXISTS `edu_video`;

CREATE TABLE `edu_video` (
  `id` char(19) NOT NULL COMMENT '视频ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) NOT NULL COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程视频';

/*Data for the table `edu_video` */

insert  into `edu_video`(`id`,`course_id`,`chapter_id`,`title`,`video_source_id`,`video_original_name`,`sort`,`play_count`,`is_free`,`duration`,`status`,`size`,`version`,`gmt_create`,`gmt_modified`) values 
('1182499307429339137','18','32','第一节','\r\n846003a7a7e14c95b7a6b123d02594a3','',0,0,0,0,'',0,1,'2019-10-11 11:32:59','2019-10-11 11:57:38'),
('1185312444399071234','14','1','12','\r\n846003a7a7e14c95b7a6b123d02594a3','',0,0,0,0,'Empty',0,1,'2019-10-19 05:51:23','2019-10-19 05:51:33'),
('1189434737808990210','18','44','测试','\r\n846003a7a7e14c95b7a6b123d02594a3','',1,0,0,0,'Empty',0,1,'2019-10-30 14:51:55','2019-10-30 14:51:55'),
('1189471423678939138','18','1181729226915577857','test','\r\n846003a7a7e14c95b7a6b123d02594a3','视频',1,0,0,0,'Empty',0,1,'2019-10-30 17:17:41','2019-10-30 17:17:41'),
('1189476403626409986','18','1181729226915577857','22','\r\n846003a7a7e14c95b7a6b123d02594a3','视频.mp4',0,0,0,0,'Empty',0,1,'2019-10-30 17:37:29','2019-10-30 17:37:29'),
('1192252824606289921','1192252213659774977','1192252428399751169','第一课时','\r\n846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,'Empty',0,1,'2019-11-07 09:29:59','2019-11-07 09:29:59'),
('1192628092797730818','1192252213659774977','1192252428399751169','第二课时','\r\n846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,1,0,'Empty',0,1,'2019-11-08 10:21:10','2019-11-08 10:21:22'),
('1192632495013380097','1192252213659774977','1192252428399751169','第三课时','846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,1,0,'Empty',0,1,'2019-11-08 10:38:40','2019-11-08 10:38:40'),
('1194117638832111617','1192252213659774977','1192252428399751169','第四课时','\r\n846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,'Empty',0,1,'2019-11-12 13:00:05','2019-11-12 13:00:05'),
('1196263770832023554','1192252213659774977','1192252428399751169','第五课时','\r\n846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',5,0,0,0,'Empty',0,1,'2019-11-18 11:08:03','2019-11-18 11:08:03'),
('17','18','15','第一节：Java简介','\r\n846003a7a7e14c95b7a6b123d02594a3','1',1,1000,1,100,'Draft',0,1,'2019-01-01 13:08:57','2019-10-11 11:26:39'),
('18','18','15','第二节：表达式和赋值语句','\r\n846003a7a7e14c95b7a6b123d02594a3','7 - How Do I Find Time for My ',2,999,1,100,'Draft',0,1,'2019-01-01 13:09:02','2019-03-08 03:30:27'),
('19','18','15','第三节：String类','\r\n846003a7a7e14c95b7a6b123d02594a3','eae2b847ef8503b81f5d5593d769dde2.mp4',3,888,0,100,'Draft',0,1,'2019-01-01 13:09:05','2019-11-12 12:50:45'),
('20','18','15','第四节：程序风格','\r\n846003a7a7e14c95b7a6b123d02594a3','00-day10总结.avi',4,666,0,100,'Draft',0,1,'2019-01-01 13:09:05','2019-10-11 09:20:09');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `course_title` varchar(100) DEFAULT NULL COMMENT '课程名称',
  `course_cover` varchar(255) DEFAULT NULL COMMENT '课程封面',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '会员手机',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '订单金额（分）',
  `pay_type` tinyint DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_order_no` (`order_no`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单';

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`order_no`,`course_id`,`course_title`,`course_cover`,`teacher_name`,`member_id`,`nickname`,`mobile`,`total_fee`,`pay_type`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1195693605513891841','1195693605555834880','18','Java精品课程','http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/06/866e9aca-b530-4f71-a690-72d4a4bfd1e7.jpg','晴天','1','小三1231','13700000001',1.00,NULL,0,0,'2019-11-16 21:22:25','2019-11-16 21:22:25'),
('1195694200178118657','1195694200186507264','18','Java精品课程','http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/06/866e9aca-b530-4f71-a690-72d4a4bfd1e7.jpg','晴天','1','小三1231','13700000001',1.00,NULL,0,0,'2019-11-16 21:24:47','2019-11-16 21:24:47'),
('1196264007411744769','1196264005255872512','1192252213659774977','java基础课程：test','https://guli-file-190513.oss-cn-beijing.aliyuncs.com/cover/default.gif','晴天','1','小三1231','13700000001',1.00,NULL,1,0,'2019-11-18 11:09:00','2019-11-18 11:10:14'),
('1196265495278174209','1196265495273979904','18','Java精品课程','http://guli-file.oss-cn-beijing.aliyuncs.com/cover/2019/03/06/866e9aca-b530-4f71-a690-72d4a4bfd1e7.jpg','晴天','1','小三1231','13700000001',1.00,NULL,0,0,'2019-11-18 11:14:54','2019-11-18 11:14:54');

/*Table structure for table `t_pay_log` */

DROP TABLE IF EXISTS `t_pay_log`;

CREATE TABLE `t_pay_log` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '支付金额（分）',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint NOT NULL DEFAULT '0' COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text COMMENT '其他属性',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付日志表';

/*Data for the table `t_pay_log` */

insert  into `t_pay_log`(`id`,`order_no`,`pay_time`,`total_fee`,`transaction_id`,`trade_state`,`pay_type`,`attr`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1194498446013001730','1194498300579704832','2019-11-13 14:13:17',1.00,'4200000469201911130676624386','SUCCESS',1,'{\"transaction_id\":\"4200000469201911130676624386\",\"nonce_str\":\"2Lc23ILl231It53M\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"5404850AA3ED0E844DE104651477F07A\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1194498300579704832\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191113141314\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2019-11-13 14:13:17','2019-11-13 14:13:17'),
('1195253787449430017','1195253049260314624','2019-11-15 16:14:44',1.00,'4200000454201911150981874895','SUCCESS',1,'{\"transaction_id\":\"4200000454201911150981874895\",\"nonce_str\":\"MAM5UM4Xhv1lItvO\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"7DBDCAF4A078B30BB3EF073E6A238C20\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1195253049260314624\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191115161440\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2019-11-15 16:14:44','2019-11-15 16:14:44'),
('1196264321397342210','1196264005255872512','2019-11-18 11:10:14',1.00,'4200000453201911184025781554','SUCCESS',1,'{\"transaction_id\":\"4200000453201911184025781554\",\"nonce_str\":\"D1dHexCLIFIxAAg2\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"C9F5CA1EE49EA7891736D73BEB423962\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1196264005255872512\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191118111011\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2019-11-18 11:10:14','2019-11-18 11:10:14');

/*Table structure for table `ucenter_member` */

DROP TABLE IF EXISTS `ucenter_member`;

CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表';

/*Data for the table `ucenter_member` */

insert  into `ucenter_member`(`id`,`openid`,`mobile`,`password`,`nickname`,`sex`,`age`,`avatar`,`sign`,`is_disabled`,`is_deleted`,`gmt_create`,`gmt_modified`) values 
('1',NULL,'13700000001','96e79218965eb72c92a549dd5a330112','小三123',1,5,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-01 12:11:33','2019-11-08 11:56:01'),
('1080736474267144193',NULL,'13700000011','96e79218965eb72c92a549dd5a330112','用户XJtDfaYeKk',1,19,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:12:45','2019-01-02 12:12:56'),
('1080736474355224577',NULL,'13700000002','96e79218965eb72c92a549dd5a330112','用户wUrNkzAPrc',1,27,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:13:56','2019-01-02 12:14:07'),
('1086387099449442306',NULL,'13520191388','96e79218965eb72c92a549dd5a330112','用户XTMUeHDAoj',2,20,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1086387099520745473',NULL,'13520191389','96e79218965eb72c92a549dd5a330112','用户vSdKeDlimn',1,21,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1086387099608825858',NULL,'13520191381','96e79218965eb72c92a549dd5a330112','用户EoyWUVXQoP',1,18,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1086387099701100545',NULL,'13520191382','96e79218965eb72c92a549dd5a330112','用户LcAYbxLNdN',2,24,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1086387099776598018',NULL,'13520191383','96e79218965eb72c92a549dd5a330112','用户dZdjcgltnk',2,25,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1086387099852095490',NULL,'13520191384','96e79218965eb72c92a549dd5a330112','用户wNHGHlxUwX',2,23,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),
('1106746895272849410','o1R-t5u2TfEVeVjO9CPGdHPNw-to',NULL,NULL,'檀梵\'',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/zZfLXcetf2Rpsibq6HbPUWKgWSJHtha9y1XBeaqluPUs6BYicW1FJaVqj7U3ozHd3iaodGKJOvY2PvqYTuCKwpyfQ/132',NULL,0,0,'2019-03-16 10:39:57','2019-03-16 10:39:57'),
('1106822699956654081',NULL,NULL,NULL,NULL,NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 15:41:10','2019-03-16 15:41:10'),
('1106823035660357634','o1R-t5i4gENwHYRb5lVFy98Z0bdk',NULL,NULL,'GaoSir',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJI53RcCuc1no02os6ZrattWGiazlPnicoZQ59zkS7phNdLEWUPDk8fzoxibAnXV1Sbx0trqXEsGhXPw/132',NULL,0,0,'2019-03-16 15:42:30','2019-03-16 15:42:30'),
('1106823041599492098',NULL,NULL,NULL,NULL,NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 15:42:32','2019-03-16 15:42:32'),
('1106823115788341250','o1R-t5l_3rnbZbn4jWwFdy6Gk6cg',NULL,NULL,'换个网名哇、',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/jJHyeM0EN2jhB70LntI3k8fEKe7W6CwykrKMgDJM4VZqCpcxibVibX397p0vmbKURGkLS4jxjGB0GpZfxCicgt07w/132',NULL,0,0,'2019-03-16 15:42:49','2019-03-16 15:42:49'),
('1106826046730227714','o1R-t5gyxumyBqt0CWcnh0S6Ya1g',NULL,NULL,'我是Helen',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDRfib8wy7A2ltERKh4VygxdjVC1x5OaOb1t9hot4JNt5agwaVLdJLcD9vJCNcxkvQnlvLYIPfrZw/132',NULL,0,0,'2019-03-16 15:54:28','2019-03-16 15:54:28'),
('1106828185829490690','o1R-t5nNlou5lRwBVgGNJFm4rbc4',NULL,NULL,' 虎头',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKxCqRzuYWQmpwiaqQEjNxbC7WicebicXQusU306jgmfoOzUcFg1qaDq5BStiblwBjw5dUOblQ2gUicQOQ/132',NULL,0,0,'2019-03-16 16:02:58','2019-03-16 16:02:58'),
('1106830599651442689','o1R-t5hZHQB1cbX7HZJsiM727_SA',NULL,NULL,'是吴啊',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ9CsqApybcs7f3Dyib9IxIh0sBqJb7LicbjU4WticJFF0PVwFvHgtbFdBwfmk3H2t3NyqmEmVx17tRA/132',NULL,0,0,'2019-03-16 16:12:34','2019-03-16 16:12:34'),
('1106830976199278593','o1R-t5meKOoyEJ3-IhWRCBKFcvzU',NULL,NULL,'我才是Helen',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epMicP9UT6mVjYWdno0OJZkOXiajG0sllJTbGJ9DYiceej2XvbDSGCK8LCF7jv1PuG2uoYlePWic9XO8A/132',NULL,0,0,'2019-03-16 16:14:03','2019-03-16 16:14:03'),
('1106831936900415490','o1R-t5jXYSWakGtnUBnKbfVT5Iok',NULL,NULL,'文若姬',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/3HEmJwpSzguqqAyzmBwqT6aicIanswZibEOicQInQJI3ZY1qmu59icJC6N7SahKqWYv24GvX5KH2fibwt0mPWcTJ3fg/132',NULL,0,0,'2019-03-16 16:17:52','2019-03-16 16:17:52'),
('1106832491064442882','o1R-t5sud081Qsa2Vb2xSKgGnf_g',NULL,NULL,'Peanut',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-03-16 16:20:04','2019-03-16 16:20:04'),
('1106833021442510849','o1R-t5lsGc3I8P5bDpHj7m_AIRvQ',NULL,NULL,'食物链终结者',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/MQ7qUmCprK9am16M1Ia1Cs3RK0qiarRrl9y8gsssBjIZeS2GwKSrnq7ZYhmrzuzDwBxSMMAofrXeLic9IBlW4M3Q/132',NULL,0,0,'2019-03-16 16:22:11','2019-03-16 16:22:11'),
('1191600824445046786',NULL,'15210078344','96e79218965eb72c92a549dd5a330112','IT妖姬',1,5,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-05 14:19:10','2019-11-08 18:04:43'),
('1191616288114163713',NULL,'17866603606','96e79218965eb72c92a549dd5a330112','xiaowu',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-05 15:20:37','2019-11-05 15:20:37'),
('1195187659054329857',NULL,'15010546384','96e79218965eb72c92a549dd5a330112','qy',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-11-15 11:51:58','2019-11-15 11:51:58');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
