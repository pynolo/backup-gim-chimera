#Preparazione del db alle API 5.0

DROP TABLE provinces;

#IDENTITIES ADD
ALTER TABLE identities ADD COLUMN `change_time` datetime NOT NULL,
ADD COLUMN `change_type` varchar(8) NOT NULL,
ADD COLUMN `replaced_by_uid` varchar(32) DEFAULT NULL,
ADD COLUMN `interest` varchar(256) DEFAULT NULL,
ADD COLUMN `job` varchar(256) DEFAULT NULL,
ADD COLUMN `school` varchar(256) DEFAULT NULL;
ALTER TABLE identities ADD INDEX `replaced_by_idx` (replaced_by_uid);
UPDATE identities set `change_time`=`last_modified`, `change_type`='update';
#IDENTITIES CHANGE
ALTER TABLE identities CHANGE COLUMN `user_uid` `identity_uid` varchar(32) NOT NULL,
CHANGE COLUMN `birth` `birth_date` date DEFAULT NULL,
CHANGE COLUMN `address` `address_street` varchar(64) DEFAULT NULL,
CHANGE COLUMN `province_code` `address_province_id` varchar(4) DEFAULT NULL,
CHANGE COLUMN `zip` `address_zip` varchar(16) DEFAULT NULL,
CHANGE COLUMN `city` `address_town` varchar(64) DEFAULT NULL;
#IDENTITIES DROP
ALTER TABLE identities DROP COLUMN `id_service`,
DROP COLUMN `last_modified`;

#LOG
ALTER TABLE log_identities CHANGE COLUMN `user_uid` `identity_uid` varchar(32) NOT NULL,
CHANGE COLUMN `id_service` `id_federation` int(11) NOT NULL,
CHANGE COLUMN `operation` `function` varchar(128) NOT NULL,
CHANGE COLUMN `parameters` `parameters` TEXT DEFAULT NULL,
CHANGE COLUMN `result` `result` varchar(256) NOT NULL;

#COUNTERS
UPDATE counters set ckey='identity_uid' where ckey='user_uid';

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

DROP TABLE IF EXISTS `federations`;
CREATE TABLE `federations` (
  `id` int(11) NOT NULL auto_increment,
  `federation_uid` varchar(32) NOT NULL,
  `access_key` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `contact` varchar(256) DEFAULT NULL,
  `can_update` bit(1) NOT NULL,
  `can_delete` bit(1) NOT NULL,
  `can_replace` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uid_idx` (`federation_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (1,'WWWSCUSTO','GE5K38I2','www.scuolastore.it','Di Marzo',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (2,'WWWGIUSCU','GS23LY880','www.giuntiscuola.it','Di Marzo',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (3,'WWWGIUAPU','EGS76X9W','www.giuntialpunto.it','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (4,'APPIOSGAP','IO67GP31','Giunti Al Punto - App iOS (now)','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (5,'APPADRGAP','AN30GP11','Giunti Al Punto - App Android','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (6,'APPWINGAP','WN97GP26','Giunti Al Punto - App Windows','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (7,'APPIOS2GAP','IO99PM87','Giunti Al Punto - App iOS (2.0)','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (8,'WWWGAPEBK','GEO47D33','ebookomaggio.giuntialpunto.it','Congiu',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (10,'WWWGIUABB','GAB34203','www.giuntiabbonamenti.it','Federici',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (11,'WWWGIUPSY','OS290223','GiuntiOS','Salsetta',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (12,'CRM','PFV21CRM','crmgiunti','Gianassi',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (13,'GESTIONESITI','LVX32J65','Gestione siti Giunti (Livia)','Federici',true,true,true);
INSERT INTO federations (id,federation_uid,access_key,name,contact,can_update,can_delete,can_replace) VALUES (14,'WWWGIUEDU','EDU66K9T71','www.giuntiedu.it','Salsetta e Biasioli',true,true,true);

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