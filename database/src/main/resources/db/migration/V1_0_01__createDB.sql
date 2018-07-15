CREATE TABLE USER (
  ID       INT                                                                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  USERNAME VARCHAR(32)                                                                NOT NULL UNIQUE,
  PASSWORD VARCHAR(255)                                                               NOT NULL,
  ROLE     ENUM ('ROLE_DRIVER', 'ROLE_MANAGER', 'ROLE_WAREHOUSEMAN', 'ROLE_RESOURCE') NOT NULL,
  EMAIL    VARCHAR(255)                                                               NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE CITY (
  ID            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  NAME          VARCHAR(127) NOT NULL UNIQUE,
  COORDINATES_X INTEGER      NOT NULL,
  COORDINATES_Y INTEGER      NOT NULL,
  CITY_INDEX    VARCHAR(3)   NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE CUSTOMER (
  ID       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PASSPORT VARCHAR(32)  NOT NULL UNIQUE,
  NAME     VARCHAR(255) NOT NULL,
  SURNAME  VARCHAR(255) NOT NULL,
  PHONE    VARCHAR(31),
  EMAIL    VARCHAR(9255)
)
  ENGINE = InnoDB;

CREATE TABLE ORDERR (
  ID          INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ORDER_INDEX VARCHAR(31) NOT NULL UNIQUE,
  OWNER_ID    INT         NOT NULL,
  COMPLETE    BOOL        NOT NULL DEFAULT FALSE,
  FOREIGN KEY (OWNER_ID) REFERENCES CUSTOMER (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE CARGO (
  ID              INT                                                                    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CARGO_INDEX     VARCHAR(63)                                                            NOT NULL UNIQUE,
  NAME            VARCHAR(63)                                                            NOT NULL,
  WEIGHT          INT                                                                    NOT NULL,
  STATE           ENUM ('PREPARED', 'ON_BOARD', 'DELIVERED', 'TRANSIENT', 'DESTINATION') NOT NULL,
  OWNER_ID        INT                                                                    NOT NULL,
  ORDER_ID        INT                                                                    NOT NULL,
  DEPARTURE_ID    INT                                                                    NOT NULL,
  DESTINATION_ID  INT                                                                    NOT NULL,
  CURRENT_CITY_ID INT                                                                    NOT NULL,
  FOREIGN KEY (CURRENT_CITY_ID) REFERENCES CITY (ID)
    ON DELETE RESTRICT,
  FOREIGN KEY (OWNER_ID) REFERENCES CUSTOMER (ID)
    ON DELETE RESTRICT,
  FOREIGN KEY (ORDER_ID) REFERENCES ORDERR (ID)
    ON DELETE CASCADE,
  FOREIGN KEY (DEPARTURE_ID) REFERENCES CITY (ID)
    ON DELETE RESTRICT,
  FOREIGN KEY (DESTINATION_ID) REFERENCES CITY (ID)
    ON DELETE RESTRICT
)
  ENGINE = InnoDB;

CREATE TABLE TRUCK (
  ID              INT                                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  REG_NUMBER      VARCHAR(7)                                 NOT NULL UNIQUE,
  BRIGADE_STR     INTEGER                                    NOT NULL,
  CAPACITY        INTEGER                                    NOT NULL,
  STATUS          ENUM ('IN_USE', 'IN_SERVICE', 'STAY_IDLE') NOT NULL,
  CURRENT_CITY_ID INT,
  FOREIGN KEY (CURRENT_CITY_ID) REFERENCES CITY (ID)
    ON DELETE SET NULL
)
  ENGINE = InnoDB;

CREATE TABLE DRIVER (
  ID               INT                                                              NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PERSONAL_NUMBER  VARCHAR(9)                                                       NOT NULL UNIQUE,
  NAME             VARCHAR(127)                                                     NOT NULL,
  SURNAME          VARCHAR(127)                                                     NOT NULL,
  EMAIL            VARCHAR(255)                                                     NOT NULL,
  WORKED_TIME      INTEGER                                                          NOT NULL,
  PAID_TIME        INTEGER                                                          NOT NULL,
  STATE            ENUM ('REST', 'ON_SHIFT', 'DRIVING', 'PORTER', 'READY_TO_ROUTE') NOT NULL,
  CURRENT_CITY_ID  INT,
  CURRENT_TRUCK_ID INT,
  FOREIGN KEY (CURRENT_CITY_ID) REFERENCES CITY (ID)
    ON DELETE SET NULL,
  FOREIGN KEY (CURRENT_TRUCK_ID) REFERENCES TRUCK (ID)
    ON DELETE SET NULL
)
  ENGINE = InnoDB;

CREATE TABLE WAYPOINT (
  ID       INT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CARGO_ID INT                     NOT NULL,
  CITY_ID  INT                     NOT NULL,
  WP_TYPE  ENUM ('LOAD', 'UNLOAD') NOT NULL,
  ORDER_ID INT                     NOT NULL,
  TRUCK_ID INT,
  COMPLETE BOOL                    NOT NULL DEFAULT FALSE,

  FOREIGN KEY (ORDER_ID) REFERENCES ORDERR (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (TRUCK_ID) REFERENCES TRUCK (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (CITY_ID) REFERENCES CITY (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (CARGO_ID) REFERENCES CARGO (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  UNIQUE (CITY_ID, CARGO_ID, WP_TYPE)
)
  ENGINE = InnoDB;

# Table for mapping Deliver_Order and Driver
CREATE TABLE WAYPOINT_DRIVERS (
  ID          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  WAYPOINT_ID INT NOT NULL,
  DRIVER_ID   INT NOT NULL,

  FOREIGN KEY (WAYPOINT_ID) REFERENCES WAYPOINT (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (DRIVER_ID) REFERENCES DRIVER (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  UNIQUE (WAYPOINT_ID, DRIVER_ID)
)
  ENGINE = InnoDB;




