DROP TABLE IF EXISTS `identities_consent`;
CREATE TABLE `identities_consent` (
  `id` int(11) NOT NULL auto_increment,
  `id_identity` int(11) NOT NULL,
  `range` string(256) NOT NULL,
  `tos` bit(1) NOT NULL,
  `marketing` bit(1) NOT NULL,
  `profiling` bit(1) NOT NULL,
  `tos_date` date NOT NULL,
  `marketing_date` date NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `identity_range_key` (`id_identity`,`range`)
) ENGINE=InnoDB AUTO_INCREMENT=0;