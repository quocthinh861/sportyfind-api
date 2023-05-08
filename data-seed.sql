DROP TABLE IF EXISTS "fieldbooking" CASCADE;
DROP TABLE IF EXISTS "payment" CASCADE;
DROP TABLE IF EXISTS "review" CASCADE;
DROP TABLE IF EXISTS "field" CASCADE;
DROP TABLE IF EXISTS "fieldtype" CASCADE;
DROP TABLE IF EXISTS "venue" CASCADE;
DROP TABLE IF EXISTS "app_user" CASCADE;
DROP TABLE IF EXISTS "sport" CASCADE;

/* Insert data into tables */
INSERT INTO sport (sportname) VALUES ('Soccer'), ('Basketball'), ('Tennis');

INSERT INTO venue (venuename, address, latitude, longitude, openinghour, closinghour)
VALUES ('ABC Sports Complex', '123 Main St', 47.6097, -122.3331, '08:00:00', '22:00:00');

INSERT INTO fieldtype (sportid, fieldtypename)
VALUES (1, 'Three-Player Field');
INSERT INTO fieldtype (sportid, fieldtypename)
VALUES (1, 'Five-Player Field');
INSERT INTO fieldtype (sportid, fieldtypename)
VALUES (1, 'Seven-Player Field');

INSERT INTO field (fieldname, fieldtypeid, venueid, sportid, length, width, area, hourlyrate)
VALUES ('Field A', 1, 1, 1, 20.0, 15.0, 300.0, 50.0);
