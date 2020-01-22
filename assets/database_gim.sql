DROP TABLE IF EXISTS `log_user_properties`;
CREATE TABLE  `log_user_properties` (
  `id` int NOT NULL auto_increment,
  `last_modified` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT 'data ultima modifica',
  `user_uid` varchar(32) NOT NULL,
  `id_service` int NOT NULL,
  PRIMARY KEY  (`id`),
  /*CONSTRAINT `log_user_uid_fk` FOREIGN KEY (`user_uid`) REFERENCES `user_properties` (`user_uid`),*/
  CONSTRAINT `log_service_fk` FOREIGN KEY (`id_service`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `user_properties`;
CREATE TABLE  `user_properties` (
  `id` int NOT NULL auto_increment,
  `last_modified` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT 'data ultima modifica',
  `user_uid` varchar(32) NOT NULL,
  `user_name` varchar(256) default NULL COMMENT 'userName importato giunti scuola',
  `email` varchar(256) default NULL,
  `province_code` varchar(2) default NULL COMMENT 'sigla provincia',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `user_uid_key` (`user_uid`),
  KEY `email_key` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `services`;
CREATE TABLE  `services` (
  `id` int NOT NULL auto_increment,
  `access_key` varchar(128) NOT NULL,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `access_key_key` (`access_key`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
insert into services (id, access_key, name) values(1,"GE5K38I2","www.scuolastore.it");
insert into services (id, access_key, name) values(2,"GS23LY880","www.giuntiscuola.it");
insert into services (id, access_key, name) values(3,"EGS76X9W","www.giuntialpunto.it");
insert into services (id, access_key, name) values(4,"IO67GP31","Giunti Al Punto - App iOS");
insert into services (id, access_key, name) values(5,"AN30GP11","Giunti Al Punto - App Android");
insert into services (id, access_key, name) values(6,"WN97GP26","Giunti Al Punto - App Windows");

DROP TABLE IF EXISTS `counters`;
CREATE TABLE  `counters` (
  `id` int NOT NULL auto_increment,
  `ckey` varchar(256) NOT NULL,
  `number` int(11) NOT NULL,
  `locked` bit NOT NULL DEFAULT FALSE,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `provinces`;
CREATE TABLE  `provinces` (
  `id` int NOT NULL auto_increment,
  `name` varchar(256) NOT NULL,
  `code` varchar(4) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code_key` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
insert into provinces (id, name, code) values(1,"Agrigento","AG");
insert into provinces (id, name, code) values(2,"Alessandria","AL");
insert into provinces (id, name, code) values(3,"Ancona","AN");
insert into provinces (id, name, code) values(4,"Aosta","AO");
insert into provinces (id, name, code) values(5,"Arezzo","AR");
insert into provinces (id, name, code) values(6,"Ascoli Piceno","AP");
insert into provinces (id, name, code) values(7,"Asti","AT");
insert into provinces (id, name, code) values(8,"Avellino","AV");
insert into provinces (id, name, code) values(9,"Bari","BA");
insert into provinces (id, name, code) values(10,"Barletta-Andria-Trani","BT");
insert into provinces (id, name, code) values(11,"Belluno","BL");
insert into provinces (id, name, code) values(12,"Benevento","BN");
insert into provinces (id, name, code) values(13,"Bergamo","BG");
insert into provinces (id, name, code) values(14,"Biella","BI");
insert into provinces (id, name, code) values(15,"Bologna","BO");
insert into provinces (id, name, code) values(16,"Bolzano","BZ");
insert into provinces (id, name, code) values(17,"Brescia","BS");
insert into provinces (id, name, code) values(18,"Brindisi","BR");
insert into provinces (id, name, code) values(19,"Cagliari","CA");
insert into provinces (id, name, code) values(20,"Caltanissetta","CL");
insert into provinces (id, name, code) values(21,"Campobasso","CB");
insert into provinces (id, name, code) values(22,"Carbonia-Iglesias","CI");
insert into provinces (id, name, code) values(23,"Caserta","CE");
insert into provinces (id, name, code) values(24,"Catania","CT");
insert into provinces (id, name, code) values(25,"Catanzaro","CZ");
insert into provinces (id, name, code) values(26,"Chieti","CH");
insert into provinces (id, name, code) values(27,"Como","CO");
insert into provinces (id, name, code) values(28,"Cosenza","CS");
insert into provinces (id, name, code) values(29,"Cremona","CR");
insert into provinces (id, name, code) values(30,"Crotone","KR");
insert into provinces (id, name, code) values(31,"Cuneo","CN");
insert into provinces (id, name, code) values(32,"Enna","EN");
insert into provinces (id, name, code) values(33,"Fermo","FM");
insert into provinces (id, name, code) values(34,"Ferrara","FE");
insert into provinces (id, name, code) values(35,"Firenze","FI");
insert into provinces (id, name, code) values(36,"Foggia","FG");
insert into provinces (id, name, code) values(37,"Forlì-Cesena","FC");
insert into provinces (id, name, code) values(38,"Frosinone","FR");
insert into provinces (id, name, code) values(39,"Genova","GE");
insert into provinces (id, name, code) values(40,"Gorizia","GO");
insert into provinces (id, name, code) values(41,"Grosseto","GR");
insert into provinces (id, name, code) values(42,"Imperia","IM");
insert into provinces (id, name, code) values(43,"Isernia","IS");
insert into provinces (id, name, code) values(44,"La Spezia","SP");
insert into provinces (id, name, code) values(45,"L\'Aquila","AQ");
insert into provinces (id, name, code) values(46,"Latina","LT");
insert into provinces (id, name, code) values(47,"Lecce","LE");
insert into provinces (id, name, code) values(48,"Lecco","LC");
insert into provinces (id, name, code) values(49,"Livorno","LI");
insert into provinces (id, name, code) values(50,"Lodi","LO");
insert into provinces (id, name, code) values(51,"Lucca","LU");
insert into provinces (id, name, code) values(52,"Macerata","MC");
insert into provinces (id, name, code) values(53,"Mantova","MN");
insert into provinces (id, name, code) values(54,"Massa-Carrara","MS");
insert into provinces (id, name, code) values(55,"Matera","MT");
insert into provinces (id, name, code) values(56,"Messina","ME");
insert into provinces (id, name, code) values(57,"Milano","MI");
insert into provinces (id, name, code) values(58,"Modena","MO");
insert into provinces (id, name, code) values(59,"Monza e della Brianza","MB");
insert into provinces (id, name, code) values(60,"Napoli","NA");
insert into provinces (id, name, code) values(61,"Novara","NO");
insert into provinces (id, name, code) values(62,"Nuoro","NU");
insert into provinces (id, name, code) values(63,"Olbia-Tempio","OT");
insert into provinces (id, name, code) values(64,"Oristano","OR");
insert into provinces (id, name, code) values(65,"Padova","PD");
insert into provinces (id, name, code) values(66,"Palermo","PA");
insert into provinces (id, name, code) values(67,"Parma","PR");
insert into provinces (id, name, code) values(68,"Pavia","PV");
insert into provinces (id, name, code) values(69,"Perugia","PG");
insert into provinces (id, name, code) values(70,"Pesaro e Urbino","PU");
insert into provinces (id, name, code) values(71,"Pescara","PE");
insert into provinces (id, name, code) values(72,"Piacenza","PC");
insert into provinces (id, name, code) values(73,"Pisa","PI");
insert into provinces (id, name, code) values(74,"Pistoia","PT");
insert into provinces (id, name, code) values(75,"Pordenone","PN");
insert into provinces (id, name, code) values(76,"Potenza","PZ");
insert into provinces (id, name, code) values(77,"Prato","PO");
insert into provinces (id, name, code) values(78,"Ragusa","RG");
insert into provinces (id, name, code) values(79,"Ravenna","RA");
insert into provinces (id, name, code) values(80,"Reggio Calabria","RC");
insert into provinces (id, name, code) values(81,"Reggio Emilia","RE");
insert into provinces (id, name, code) values(82,"Rieti","RI");
insert into provinces (id, name, code) values(83,"Rimini","RN");
insert into provinces (id, name, code) values(84,"Roma","RM");
insert into provinces (id, name, code) values(85,"Rovigo","RO");
insert into provinces (id, name, code) values(86,"Salerno","SA");
insert into provinces (id, name, code) values(87,"Medio Campidano","VS");
insert into provinces (id, name, code) values(88,"Sassari","SS");
insert into provinces (id, name, code) values(89,"Savona","SV");
insert into provinces (id, name, code) values(90,"Siena","SI");
insert into provinces (id, name, code) values(91,"Siracusa","SR");
insert into provinces (id, name, code) values(92,"Sondrio","SO");
insert into provinces (id, name, code) values(93,"Taranto","TA");
insert into provinces (id, name, code) values(94,"Teramo","TE");
insert into provinces (id, name, code) values(95,"Terni","TR");
insert into provinces (id, name, code) values(96,"Torino","TO");
insert into provinces (id, name, code) values(97,"Ogliastra","OG");
insert into provinces (id, name, code) values(98,"Trapani","TP");
insert into provinces (id, name, code) values(99,"Trento","TN");
insert into provinces (id, name, code) values(100,"Treviso","TV");
insert into provinces (id, name, code) values(101,"Trieste","TS");
insert into provinces (id, name, code) values(102,"Udine","UD");
insert into provinces (id, name, code) values(103,"Varese","VA");
insert into provinces (id, name, code) values(104,"Venezia","VE");
insert into provinces (id, name, code) values(105,"Verbano-Cusio-Ossola","VB");
insert into provinces (id, name, code) values(106,"Vercelli","VC");
insert into provinces (id, name, code) values(107,"Verona","VR");
insert into provinces (id, name, code) values(108,"Vibo Valentia","VV");
insert into provinces (id, name, code) values(109,"Vicenza","VI");
insert into provinces (id, name, code) values(110,"Viterbo","VT");

