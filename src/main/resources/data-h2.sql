INSERT INTO Books(title, status) VALUES('Diune', 'BORROWED');
INSERT INTO Books(title, status) VALUES('Ender Game', 'AVAIABLE');

INSERT INTO Users(first_name, last_name, email, password, role) VALUES('Bob', 'Dylan', 'bob@dylan', '$2a$10$nvYQSvp793H5hBDSqT5/CuW6D7JVe0nIwH48LDqkQyyWyifsqXbMq', 'ROLE_USER');
INSERT INTO Users(first_name, last_name, email, password, role) VALUES('Bob', 'Builder', 'admin', '$2a$10$yzSrF3ZI7aNH.54OrBqQJ.N79OhFZA3pqWtXN6TpnMQi76JW2k2.O', 'ROLE_ADMIN');

INSERT INTO Issues(user_id, book_id, issue_date) VALUES(1, 1, '2017-08-12');

INSERT INTO Returns(user_id, book_id, issue_date, return_date) VALUES(1, 2, '2017-08-12', '2017-08-16');
INSERT INTO Returns(user_id, book_id, issue_date, return_date) VALUES(2, 1, '2017-08-10', '2017-08-12');