INSERT INTO station (name, latitude, longitude) VALUES ('Moscow', 55.751244, 37.618423);
INSERT INTO station (name, latitude, longitude) VALUES ('Voronezh', 51.67204, 39.1843);
INSERT INTO station (name, latitude, longitude) VALUES ('Saratov', 51.592365, 45.960804);
INSERT INTO station (name, latitude, longitude) VALUES ('Samara', 53.241505, 50.221245);
INSERT INTO station (name, latitude, longitude) VALUES ('Kazan', 55.78874, 49.12214);
INSERT INTO station (name, latitude, longitude) VALUES ('Nizhniy-Novgorod', 56.296505, 43.936058);

-- ROUTE 1
INSERT INTO route (route_id, station_id, station_order) VALUES (1, 1, 1);
INSERT INTO route (route_id, station_id, station_order) VALUES (1, 6, 2);
INSERT INTO route (route_id, station_id, station_order) VALUES (1, 5, 3);
INSERT INTO route (route_id, station_id, station_order) VALUES (1, 4, 4);
INSERT INTO route (route_id, station_id, station_order) VALUES (1, 3, 5);
-- ROUTE 2
INSERT INTO route (route_id, station_id, station_order) VALUES (2, 1, 1);
INSERT INTO route (route_id, station_id, station_order) VALUES (2, 2, 2);
INSERT INTO route (route_id, station_id, station_order) VALUES (2, 3, 3);
-- ROUTE 3
INSERT INTO route (route_id, station_id, station_order) VALUES (3, 1, 1);
INSERT INTO route (route_id, station_id, station_order) VALUES (3, 6, 2);
INSERT INTO route (route_id, station_id, station_order) VALUES (3, 3, 3);
-- ROUTE 4
INSERT INTO route (route_id, station_id, station_order) VALUES (4, 6, 3);
INSERT INTO route (route_id, station_id, station_order) VALUES (4, 4, 4);
INSERT INTO route (route_id, station_id, station_order) VALUES (4, 5, 1);
INSERT INTO route (route_id, station_id, station_order) VALUES (4, 2, 2);


INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("train1", 100, 66, 12);
INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("train2", 149, 54, 16);
INSERT INTO train (name, speed, seats_amount, carriage_amount) VALUES ("train3", 180, 36, 18);

INSERT INTO user (username, email, role, password) VALUES ("admin", "test@mail.ru", 0, "21232f297a57a5a743894a0e4a801fc3");
INSERT INTO user (username, email, role, password) VALUES ("user", "test@mail.ru", 1, "ee11cbb19052e40b07aac0ca060c23ee");