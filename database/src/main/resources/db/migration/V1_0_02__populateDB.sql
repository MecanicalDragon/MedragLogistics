INSERT INTO customer (PASSPORT, NAME, SURNAME, PHONE, EMAIL) VALUES ('RUSSIAN-01', 'VICTOR', 'POPOV', '89312457896', 'POPOV@MAIL.RU');
INSERT INTO customer (PASSPORT, NAME, SURNAME, PHONE, EMAIL) VALUES ('RUSSIAN-02', 'STANISLAV', 'ALESHIN', '89665487965', 'ALESHIN@MAIL.RU');
INSERT INTO customer (PASSPORT, NAME, SURNAME, PHONE, EMAIL) VALUES ('RUSSIAN-03', 'ALEXEY', 'TRACTOROV', '89116825256', 'TRACTOROV@MAIL.RU');

INSERT INTO city (NAME, COORDINATES_X, COORDINATES_Y, CITY_INDEX) VALUES ('Velikie Luki', 128, 128, 'VLK');
INSERT INTO city (NAME, COORDINATES_X, COORDINATES_Y, CITY_INDEX) VALUES ('Moscow', 512, 512, 'MSK');
INSERT INTO city (NAME, COORDINATES_X, COORDINATES_Y, CITY_INDEX) VALUES ('Piter', 256, 256, 'SPB');

INSERT INTO cargo (CARGO_INDEX, NAME, WEIGHT, STATE, OWNER_ID, DEPARTURE_ID, DESTINATION_ID) VALUES ('CAR-VLK-SPB-1806171543253590', 'toys', '50.54', 'PREPARED', 1, 1, 3);
INSERT INTO cargo (CARGO_INDEX, NAME, WEIGHT, STATE, OWNER_ID, DEPARTURE_ID, DESTINATION_ID) VALUES ('CAR-SPB-MSK-1806171543253590', 'plates', '34.25', 'PREPARED', 1, 3, 2);
INSERT INTO cargo (CARGO_INDEX, NAME, WEIGHT, STATE, OWNER_ID, DEPARTURE_ID, DESTINATION_ID) VALUES ('CAR-MSK-VLK-1806171543253590', 'guns', '126.34', 'PREPARED', 1, 2, 1);

INSERT INTO orderr (ORDER_INDEX, OWNER_ID, IMPLEMENTED) VALUES ('ORD-VL-SPB-20180630-001', 1, FALSE);
INSERT INTO orderr (ORDER_INDEX, OWNER_ID, IMPLEMENTED) VALUES ('ORD-VL-SPB-20180630-002', 2, FALSE);
INSERT INTO orderr (ORDER_INDEX, OWNER_ID, IMPLEMENTED) VALUES ('ORD-VL-SPB-20180630-003', 3, FALSE);

INSERT INTO user (USERNAME, PASSWORD, ROLE) VALUES ('MGR-123', '$2a$12$ct0FXevB.HXS4WVOjK1N3eBXYtuppH8q.54WmMa/3UKSg2.mT4TGi', 'ROLE_MANAGER');
INSERT INTO user (USERNAME, PASSWORD, ROLE) VALUES ('WHM-123', '$2a$12$ct0FXevB.HXS4WVOjK1N3eBXYtuppH8q.54WmMa/3UKSg2.mT4TGi', 'ROLE_WAREHOUSEMAN');
INSERT INTO user (USERNAME, PASSWORD, ROLE) VALUES ('DRV-123', '$2a$12$ct0FXevB.HXS4WVOjK1N3eBXYtuppH8q.54WmMa/3UKSg2.mT4TGi', 'ROLE_DRIVER');
INSERT INTO user (USERNAME, PASSWORD, ROLE) VALUES ('RSM-123', '$2a$12$ct0FXevB.HXS4WVOjK1N3eBXYtuppH8q.54WmMa/3UKSg2.mT4TGi', 'ROLE_RESOURCE');