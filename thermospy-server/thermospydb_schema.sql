-- Timestamp: 2015-02-25 20:22:34.118
-- Source database is: thermospydb
-- Connection URL is: jdbc:derby://localhost:1527/thermospydb;user=thermospy;password=thermospy
-- appendLogs: false
-- Executed command: {JAVA_HOME}/db/bin/dblook -d 'jdbc:derby://localhost:1527/thermospydb;user=thermospy;password=thermospy'
-- ----------------------------------------------
-- DDL Statements for schemas
-- ----------------------------------------------

CREATE SCHEMA "THERMOSPY";

-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE "THERMOSPY"."TEMPERATUREENTRY" ("ID" INTEGER NOT NULL, "TEMPERATURE" DOUBLE, "TIMESTAMP" TIMESTAMP NOT NULL, "FK_SESSION_ID" INTEGER);

CREATE TABLE "THERMOSPY"."SESSION" ("ID" INTEGER NOT NULL, "NAME" VARCHAR(50), "START_TIMESTAMP" TIMESTAMP, "END_TIMESTAMP" TIMESTAMP, "COMMENT" LONG VARCHAR, "TARGETTEMPERATURE" DECIMAL(5,0), "ISOPEN" BOOLEAN);

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- PRIMARY/UNIQUE
ALTER TABLE "THERMOSPY"."TEMPERATUREENTRY" ADD CONSTRAINT "SQL150213181304160" PRIMARY KEY ("ID");

ALTER TABLE "THERMOSPY"."SESSION" ADD CONSTRAINT "SQL150213183523380" PRIMARY KEY ("ID");

-- FOREIGN
ALTER TABLE "THERMOSPY"."TEMPERATUREENTRY" ADD CONSTRAINT "SQL150213183811550" FOREIGN KEY ("FK_SESSION_ID") REFERENCES "THERMOSPY"."SESSION" ("ID") ON DELETE NO ACTION ON UPDATE NO ACTION;

