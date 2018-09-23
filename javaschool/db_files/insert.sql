INSERT INTO station (name, latitude, longitude) VALUES ('Moscow', 55.751244, 37.618423);
INSERT INTO station (name, latitude, longitude) VALUES ('Voronezh', 51.67204, 39.1843);
INSERT INTO station (name, latitude, longitude) VALUES ('Saratov', 51.592365, 45.960804);
INSERT INTO station (name, latitude, longitude) VALUES ('Samara', 53.241505, 50.221245);
INSERT INTO station (name, latitude, longitude) VALUES ('Kazan', 55.78874, 49.12214);
INSERT INTO station (name, latitude, longitude) VALUES ('Nizhniy-Novgorod', 56.296505, 43.936058);
INSERT INTO station (name, latitude, longitude) VALUES ('Perm', 58.0297, 56.2668);
INSERT INTO station (name, latitude, longitude) VALUES ('Orenburg', 51.7666, 55.1005);
INSERT INTO station (name, latitude, longitude) VALUES ('Volgograd', 48.7080, 44.5133);
INSERT INTO station (name, latitude, longitude) VALUES ('Saint-Petersburg', 59.9343, 30.3351);
INSERT INTO station (name, latitude, longitude) VALUES ('Ufa', 54.7388, 55.9721);
INSERT INTO station (name, latitude, longitude) VALUES ('Omsk', 54.9885, 73.3242);
INSERT INTO station (name, latitude, longitude) VALUES ('Chelyabinsk', 55.1644, 61.4368);
INSERT INTO station (name, latitude, longitude) VALUES ('Ekaterinburg', 56.8389, 60.6057);


INSERT INTO `direction` VALUES (60,1,6),(63,1,2),(64,1,10),(72,2,3),(73,2,9),(74,2,1),(69,3,4),(70,3,9),(71,3,2),(75,4,3),(76,4,5),(77,4,11),(78,4,8),(65,5,7),(66,5,6),(67,5,4),(68,5,11),(61,6,5),(62,6,1),(92,7,14),(79,8,4),(83,8,11),(93,10,1),(80,11,4),(81,11,5),(82,11,8),(84,11,13),(91,11,14),(94,12,13),(85,13,12),(86,13,14),(87,13,11),(88,14,13),(89,14,7),(90,14,11);

INSERT INTO `route` VALUES (39,2,1,1),(40,2,6,2),(41,2,5,3),(42,2,4,4),(43,2,8,5),(44,3,2,1),(45,3,3,2),(46,3,4,3),(47,3,11,4),(48,3,13,5),(49,4,13,1),(50,4,11,2),(51,4,4,3),(52,4,3,4),(53,4,9,5);

INSERT INTO `schedule` VALUES (63,16,13,NULL,NULL,'2018-08-21 13:00:00','00:00:00'),(64,16,11,'2018-08-21 16:31:13','00:05:00','2018-08-21 16:36:13','00:00:00'),(65,16,4,'2018-08-21 20:37:49','00:05:00','2018-08-21 20:42:49','00:00:00'),(66,16,3,'2018-08-22 00:03:05','00:05:00','2018-08-22 00:08:05','00:00:00'),(67,16,9,'2018-08-22 03:25:12',NULL,NULL,'00:00:00'),(68,17,13,NULL,NULL,'2018-08-22 21:20:00','00:00:00'),(69,17,11,'2018-08-23 00:51:13','00:05:00','2018-08-23 00:56:13','00:00:00'),(70,17,4,'2018-08-23 04:57:49','00:05:00','2018-08-23 05:02:49','00:00:00'),(71,17,3,'2018-08-23 08:23:05','00:05:00','2018-08-23 08:28:05','00:00:00'),(72,17,9,'2018-08-23 11:45:12',NULL,NULL,'00:00:00'),(73,18,1,NULL,NULL,'2018-08-21 22:20:00','00:00:00'),(74,18,6,'2018-08-22 02:18:15','00:05:00','2018-08-22 02:23:15','00:00:00'),(75,18,5,'2018-08-22 05:34:24','00:05:00','2018-08-22 05:39:24','00:00:00'),(76,18,4,'2018-08-22 08:29:35','00:05:00','2018-08-22 08:34:35','00:00:00'),(77,18,8,'2018-08-22 12:10:45',NULL,NULL,'00:00:00'),(78,19,2,NULL,NULL,'2018-08-22 21:20:00','00:00:00'),(79,19,3,'2018-08-23 02:00:34','00:05:00','2018-08-23 02:05:34','00:00:00'),(80,19,4,'2018-08-23 05:25:50','00:05:00','2018-08-23 05:30:50','00:00:00'),(81,19,11,'2018-08-23 09:32:26','00:05:00','2018-08-23 09:37:26','00:00:00'),(82,19,13,'2018-08-23 13:03:39',NULL,NULL,'00:00:00');

INSERT INTO `trip` VALUES (16,1,4,'2018-08-21 13:00:00',1),(17,1,4,'2018-08-22 21:20:00',1),(18,1,2,'2018-08-21 22:20:00',1),(19,1,3,'2018-08-22 21:20:00',1);

INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("E048", 100, 66, 12);
INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("F821", 149, 54, 16);
INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("721F", 180, 36, 18);


INSERT INTO user (username, email, firstname, lastname, birthday, role, password) VALUES ("admin", "test@mail.ru", "Maksim", "Bogdanov", "2018-10-24", 0, "21232f297a57a5a743894a0e4a801fc3");
INSERT INTO user (username, email, firstname, lastname, birthday, role, password) VALUES ("user", "test@yandex.ru", "Stas", "Tretyakov", "2018-11-21", 1, "ee11cbb19052e40b07aac0ca060c23ee");
