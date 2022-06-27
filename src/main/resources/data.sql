INSERT INTO flight (carrier_code, flight_number, flight_date, origin_airport_code, destination_airport_code) VALUES
    ('AC', '1010', PARSEDATETIME('27-06-2022', 'dd-MM-yyyy'), 'USA', 'ABI'),
    ('AF', '4234', PARSEDATETIME('27-06-2022', 'dd-MM-yyyy'), 'ADA', 'LEI'),
    ('AR', '028', PARSEDATETIME('28-06-2022', 'dd-MM-yyyy'), 'AGA', 'NCY'),
    ('EI', '417', PARSEDATETIME('29-06-2022', 'dd-MM-yyyy'), 'SUM', 'ESB'),
    ('LH', '2008', PARSEDATETIME('01-07-2022', 'dd-MM-yyyy'), 'AYU', 'TNR');

INSERT INTO users (username, password, role_name) VALUES
    ('user', 'user', 'ROLE_USER'),
    ('admin', 'admin', 'ROLE_ADMIN');