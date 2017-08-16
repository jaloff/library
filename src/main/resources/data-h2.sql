INSERT INTO Books(title, status) VALUES('Diuna', 'BORROWED');
INSERT INTO Books(title, status) VALUES('Ender Game', 'AVAIABLE');

INSERT INTO Users(first_name, last_name, email, password, role) VALUES('Bob', 'Dylan', 'bob@dylan', 'password', 'ROLE_USER');
INSERT INTO Users(first_name, last_name, email, password, role) VALUES('Bob', 'Builder', 'admin', 'admin', 'ROLE_ADMIN');

INSERT INTO Issues(user_id, book_id, issue_date) VALUES(1, 1, '2017-08-12');