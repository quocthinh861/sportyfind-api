DROP TABLE IF EXISTS "fieldbooking" CASCADE;
DROP TABLE IF EXISTS "payment" CASCADE;
DROP TABLE IF EXISTS "review" CASCADE;
DROP TABLE IF EXISTS "field" CASCADE;
DROP TABLE IF EXISTS "fieldtype" CASCADE;
DROP TABLE IF EXISTS "venue" CASCADE;
DROP TABLE IF EXISTS "app_user" CASCADE;
DROP TABLE IF EXISTS "sport" CASCADE;

CREATE TABLE "app_user" (
                            "userid" SERIAL PRIMARY KEY,
                            "usertype" VARCHAR(20) NOT NULL,
                            "firstname" VARCHAR(255) NOT NULL,
                            "lastname" VARCHAR(255) NOT NULL,
                            "phone" VARCHAR(255) NOT NULL,
                            "venueid" INT
);

CREATE TABLE "venue" (
                         venueid SERIAL PRIMARY KEY,
                         userid INT REFERENCES "user"("userid"),
                         venuename VARCHAR(255),
                         address VARCHAR(255),
                         latitude NUMERIC(10, 6),
                         longitude NUMERIC(10, 6),
                         openinghour TIMESTAMP WITHOUT TIME ZONE,
                         closinghour TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "sport" (
                         sportid SERIAL PRIMARY KEY,
                         sportname VARCHAR(255)
);

CREATE TABLE "fieldtype" (
                             fieldtypeid SERIAL PRIMARY KEY,
                             sportid INT REFERENCES "sport"("sportid"),
                             fieldtypename VARCHAR(255)
);

CREATE TABLE "field" (
                         fieldid SERIAL PRIMARY KEY,
                         fieldname VARCHAR(255),
                         fieldtypeid INT REFERENCES "fieldtype"("fieldtypeid"),
                         venueid INT REFERENCES "venue"("venueid"),
                         sportid INT REFERENCES "sport"("sportid"),
                         length NUMERIC(10, 6),
                         width NUMERIC(10, 6),
                         area NUMERIC(10, 6),
                         hourlyrate NUMERIC(10, 6)
);

CREATE TABLE "review" (
                          venueid INT REFERENCES "venue"("venueid"),
                          userid INT REFERENCES "user"("userid"),
                          createddate TIMESTAMP WITHOUT TIME ZONE,
                          score NUMERIC(10, 6),
                          review VARCHAR(255)
);

CREATE TABLE "fieldbooking" (
                                fieldbookingid SERIAL PRIMARY KEY,
                                fieldid INT REFERENCES "field"("fieldid"),
                                customerid INT REFERENCES "user"("userid"),
                                starttime TIMESTAMP WITHOUT TIME ZONE,
                                endtime TIMESTAMP WITHOUT TIME ZONE,
                                bookingstatus VARCHAR(255)
);

CREATE TABLE "payment" (
                           paymentid SERIAL PRIMARY KEY,
                           bookingid INT REFERENCES "fieldbooking"("fieldbookingid"),
                           depositamount NUMERIC(10, 6),
                           totalamount NUMERIC(10, 6),
                           paymentdate TIMESTAMP WITHOUT TIME ZONE,
                           paymentmethod VARCHAR(255),
                           paymentstatus VARCHAR(255)
);

ALTER TABLE "field" ADD FOREIGN KEY (fieldtypeid) REFERENCES "fieldtype"("fieldtypeid");

ALTER TABLE "field" ADD FOREIGN KEY (venueid) REFERENCES "venue"("venueid");

ALTER TABLE "field" ADD FOREIGN KEY (sportid) REFERENCES "sport"("sportid");

ALTER TABLE "fieldtype" ADD FOREIGN KEY (sportid) REFERENCES "sport"("sportid");

ALTER TABLE "review" ADD FOREIGN KEY (venueid) REFERENCES "venue"("venueid");

ALTER TABLE "review" ADD FOREIGN KEY (userid) REFERENCES "user"("userid");

ALTER TABLE "payment" ADD FOREIGN KEY (bookingid) REFERENCES "fieldbooking"("fieldbookingid");

ALTER TABLE "fieldbooking" ADD FOREIGN KEY (fieldid) REFERENCES "field"("fieldid");

ALTER TABLE "fieldbooking" ADD FOREIGN KEY (customerid) REFERENCES "user"("userid");


/* Insert data into tables */
INSERT INTO app_user (usertype, firstname, lastname, phone, venueid, username, password)
VALUES ('customer', 'John', 'Doe', '555-1234', 1, 'johndoe', 'mypassword');