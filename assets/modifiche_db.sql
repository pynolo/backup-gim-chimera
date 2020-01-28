DROP TABLE IF EXISTS `identities_consent`;
CREATE TABLE `identities_consent` (
  `id` int(11) NOT NULL auto_increment,
  `id_identity` int(11) NOT NULL,
  `range` varchar(64) NOT NULL,
  `tos` bit(1) NOT NULL,
  `marketing` bit(1) NOT NULL,
  `profiling` bit(1) NOT NULL,
  `tos_date` date NOT NULL,
  `marketing_date` date NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `identity_range_key` (`id_identity`,`range`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

ALTER TABLE identities ADD COLUMN `identity_uid_old` varchar(32) DEFAULT NULL,
ADD COLUMN `interest` varchar(256) DEFAULT NULL,
ADD COLUMN `job` varchar(256) DEFAULT NULL,
ADD COLUMN `school` varchar(256) DEFAULT NULL;

DROP TABLE IF EXISTS `federations`;
CREATE TABLE `federations` (
  `id` int(11) NOT NULL auto_increment,
  `federation_uid` varchar(32) NOT NULL,
  `access_key` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `contact` varchar(256),
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uid_idx` (`federation_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (1,'WWWSCUSTO','GE5K38I2','www.scuolastore.it','Di Marzo');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (2,'WWWGIUSCU','GS23LY880','www.giuntiscuola.it','Di Marzo');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (3,'WWWGIUAPU','EGS76X9W','www.giuntialpunto.it','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (4,'APPIOSGAP','IO67GP31','Giunti Al Punto - App iOS (now)','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (5,'APPADRGAP','AN30GP11','Giunti Al Punto - App Android','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (6,'APPWINGAP','WN97GP26','Giunti Al Punto - App Windows','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (7,'APPIOS2GAP','IO99PM87','Giunti Al Punto - App iOS (2.0)','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (8,'WWWGAPEBK','GEO47D33','ebookomaggio.giuntialpunto.it','Congiu');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (10,'WWWGIUABB','GAB34203','www.giuntiabbonamenti.it','Federici');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (11,'WWWGIUPSY','OS290223','GiuntiOS','Salsetta');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (12,'CRM','PFV21CRM','crmgiunti','Gianassi');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (13,'GESTIONESITI','LVX32J65','Gestione siti Giunti (Livia)','Federici');
INSERT INTO federations (id,federation_uid,access_key,name,contact) VALUES (14,'WWWGIUEDU','EDU66K9T71','www.giuntiedu.it','Salsetta e Biasioli');

DROP TABLE IF EXISTS `identities_federations`;
CREATE TABLE `identities_federations` (
  `id` int(11) NOT NULL auto_increment,
  `id_identity` int(11) NOT NULL,
  `id_federation` int(11) NOT NULL,
  `first_access` datetime DEFAULT NULL,
  `last_access` datetime DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `identity_federation_idx` (`id_identity`,`id_federation`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
INSERT INTO `identities_federations` SELECT id,id_identity,id_service as id_federation,null,null FROM identities_services;
#DROP TABLE `services`;

ALTER TABLE log_identities CHANGE COLUMN `id_service` `id_federation` int(11) NOT NULL;
ALTER TABLE identities DROP COLUMN `id_service`;

DROP TABLE IF EXISTS `identities_newsletter`;
CREATE TABLE `identities_newsletter` (
  `id` int(11) NOT NULL auto_increment,
  `id_identity` int(11) NOT NULL,
  `id_newsletter` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `identity_range_key` (`id_identity`,`id_newsletter`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_newsletters`;
CREATE TABLE `lookup_newsletters` (
  `id` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_interests`;
CREATE TABLE `lookup_interests` (
  `id` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_jobs`;
CREATE TABLE `lookup_jobs` (
  `id` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_schools`;
CREATE TABLE `lookup_schools` (
  `id` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;