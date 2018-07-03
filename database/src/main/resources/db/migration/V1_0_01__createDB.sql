CREATE TABLE USER (
  ID       INT                                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  USERNAME VARCHAR(32)                          NOT NULL UNIQUE,
  PASSWORD VARCHAR(255)                         NOT NULL,
  ROLE     ENUM ('ROLE_DRIVER', 'ROLE_MANAGER', 'ROLE_WAREHOUSEMAN') NOT NULL
)
  ENGINE = InnoDB;

INSERT INTO USER (USERNAME, PASSWORD, ROLE)
VALUES ('DRV-12345', '$2a$12$//0hyyCXAfAx91sOC0dft.AyCyV3CJYSUvIuvpzDJtZw3tKJf5tUS', 'ROLE_DRIVER');
INSERT INTO USER (USERNAME, PASSWORD, ROLE)
VALUES ('MGR-12345', '$2a$12$//0hyyCXAfAx91sOC0dft.AyCyV3CJYSUvIuvpzDJtZw3tKJf5tUS', 'ROLE_MANAGER');

CREATE TABLE CUSTOMER (
  ID       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PASSPORT VARCHAR(32)  NOT NULL UNIQUE,
  NAME     VARCHAR(255) NOT NULL,
  SURNAME  VARCHAR(255) NOT NULL,
  PHONE    VARCHAR(31),
  EMAIL    VARCHAR(9255)
)
  ENGINE = InnoDB;

CREATE TABLE CARGO (
  ID           INT                                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CARGO_NUMBER VARCHAR(63)                                NOT NULL UNIQUE,
  NAME         VARCHAR(63)                                NOT NULL,
  WEIGHT       INTEGER                                    NOT NULL,
  STATE        ENUM ('PREPARED', 'ON_BOARD', 'DELIVERED') NOT NULL,
  OWNER_ID     INT                                        NOT NULL,
  FOREIGN KEY (OWNER_ID) REFERENCES CUSTOMER (ID)
    ON UPDATE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE CITY (
  ID            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  NAME          VARCHAR(127) NOT NULL UNIQUE,
  COORDINATES_X INTEGER      NOT NULL,
  COORDINATES_Y INTEGER      NOT NULL
  #   ?DISTANCES?
)
  ENGINE = InnoDB;

CREATE TABLE TRUCK (
  ID              INT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  REG_NUMBER      VARCHAR(7) NOT NULL UNIQUE,
  BRIGADE_STR     INTEGER    NOT NULL,
  CAPACITY        INTEGER    NOT NULL,
  STATE           BOOLEAN    NOT NULL DEFAULT TRUE,
  CURRENT_CITY_ID INT        NOT NULL,
  FOREIGN KEY (CURRENT_CITY_ID) REFERENCES CITY (ID)
    ON UPDATE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE DRIVER (
  ID               INT                                            NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PERSONAL_NUMBER  VARCHAR(9)                                     NOT NULL UNIQUE,
  NAME             VARCHAR(127)                                   NOT NULL,
  SURNAME          VARCHAR(127)                                   NOT NULL,
  WORKED_TIME      INTEGER                                        NOT NULL,
  STATE            ENUM ('REST', 'ON_SHIFT', 'DRIVING', 'PORTER') NOT NULL,
  CURRENT_CITY_ID  INT,
  CURRENT_TRUCK_ID INT,

  FOREIGN KEY (CURRENT_CITY_ID) REFERENCES CITY (ID)
    ON UPDATE CASCADE,
  FOREIGN KEY (CURRENT_TRUCK_ID) REFERENCES TRUCK (ID)
    ON UPDATE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE ORDERR (
  ID           INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ORDER_NUMBER VARCHAR(31) NOT NULL UNIQUE,
  OWNER_ID     INT         NOT NULL,
  IMPLEMENTED  BOOL        NOT NULL DEFAULT FALSE,

  FOREIGN KEY (OWNER_ID) REFERENCES CUSTOMER (ID)
    ON UPDATE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE WAYPOINT (
  ID       INT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CARGO_ID INT                     NOT NULL,
  CITY_ID  INT                     NOT NULL,
  WP_TYPE  ENUM ('LOAD', 'UNLOAD') NOT NULL,
  TRUCK_ID INT,

  FOREIGN KEY (TRUCK_ID) REFERENCES TRUCK (ID)
    ON UPDATE CASCADE,
  FOREIGN KEY (CITY_ID) REFERENCES CITY (ID)
    ON UPDATE CASCADE,
  FOREIGN KEY (CARGO_ID) REFERENCES CARGO (ID)
    ON UPDATE CASCADE,

  UNIQUE (CITY_ID, CARGO_ID, WP_TYPE)
)
  ENGINE = InnoDB;

# Table for mapping Deliver_Order and Driver
CREATE TABLE WAYPOINT_DRIVERS (
  ID          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  WAYPOINT_ID INT NOT NULL,
  DRIVER_ID   INT NOT NULL,

  FOREIGN KEY (WAYPOINT_ID) REFERENCES WAYPOINT (ID),
  FOREIGN KEY (DRIVER_ID) REFERENCES DRIVER (ID),

  UNIQUE (WAYPOINT_ID, DRIVER_ID)
)
  ENGINE = InnoDB;




