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

DROP TABLE IF EXISTS `identities_newsletter`;
CREATE TABLE `identities_newsletter` (
  `id` int(11) NOT NULL auto_increment,
  `id_identity` int(11) NOT NULL,
  `name` varchar(64) NOT NULL
  PRIMARY KEY  (`id`),
  UNIQUE KEY `identity_range_key` (`id_identity`,`newsletter`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_newsletters`;
CREATE TABLE `lookup_newsletters` (
  `id` varchar(64) NOT NULL
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_interests`;
CREATE TABLE `lookup_interests` (
  `id` varchar(64) NOT NULL
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_jobs`;
CREATE TABLE `lookup_jobs` (
  `id` varchar(64) NOT NULL
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `lookup_schools`;
CREATE TABLE `lookup_interests` (
  `id` varchar(64) NOT NULL
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
