create table offices
(
    officeCode   varchar(10) not null
        primary key,
    city         varchar(50) not null,
    phone        varchar(50) not null,
    addressLine1 varchar(50) not null,
    addressLine2 varchar(50) null,
    state        varchar(50) null comment 'E(CA=ca MA=ma NY=ny Chiyoda-Ku=chiyoda)',
    country      varchar(50) not null,
    postalCode   varchar(15) not null,
    territory    varchar(10) not null
);

INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('1', 'San Francisco', '+1 650 219 4782', '100 Market Street', 'Suite 300', 'CA', 'USA', '94080', 'NA');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('2', 'Boston', '+1 215 837 0825', '1550 Court Place', 'Suite 102', 'MA', 'USA', '02107', 'NA');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('3', 'NYC', '+1 212 555 3000', '523 East 53rd Street', 'apt. 5A', 'NY', 'USA', '10022', 'NA');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('4', 'Paris', '+33 14 723 4404', '43 Rue Jouffroy D\'abbans', null, null, 'France', '75017', 'EMEA');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('5', 'Tokyo', '+81 33 224 5000', '4-1 Kioicho', null, 'Chiyoda-Ku', 'Japan', '102-8578', 'Japan');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('6', 'Sydney', '+61 2 9264 2451', '5-11 Wentworth Avenue', 'Floor #2', null, 'Australia', 'NSW 2010', 'APAC');
INSERT INTO beginningmind.offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES ('7', 'London', '+44 20 7877 2041', '25 Old Broad Street', 'Level 7', null, 'UK', 'EC2N 1HN', 'EMEA');
