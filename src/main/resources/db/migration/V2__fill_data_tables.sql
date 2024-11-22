-- Пароль 1
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Иван Иванов', '1985-05-15', '$2y$10$eYiODmv/BEQp7xOstmUqBuH0.etq92a4B5IqvjlGasxY80rBzCUka'),
('Петр Петров', '1990-07-20', '$2y$10$eYiODmv/BEQp7xOstmUqBuH0.etq92a4B5IqvjlGasxY80rBzCUka'),
('Светлана Сидорова', '1982-03-30', '$2y$10$eYiODmv/BEQp7xOstmUqBuH0.etq92a4B5IqvjlGasxY80rBzCUka'),
('Анна Антонова', '1995-11-11', '$2y$10$eYiODmv/BEQp7xOstmUqBuH0.etq92a4B5IqvjlGasxY80rBzCUka'),
('Дмитрий Дмитриев', '1988-02-28', '$2y$10$eYiODmv/BEQp7xOstmUqBuH0.etq92a4B5IqvjlGasxY80rBzCUka');

INSERT INTO account (user_id, balance)
VALUES (1, 100.00),
(2, 150.50),
(3, 200.75),
(4, 250.00),
(5, 300.25);

INSERT INTO phone_data (user_id, phone)
VALUES (1, '1234567890'),
(1, '1234567891'),
(2, '1234567892'),
(3, '1234567893'),
(3, '1234567894'),
(4, '1234567895'),
(5, '1234567896');

INSERT INTO email_data (user_id, email)
VALUES (1, 'ivan.ivanov@example.com'),
(1, 'ivan.ivanov@domain.com'),
(2, 'petr.petrov@example.com'),
(3, 'svetlana.sidorova@example.com'),
(4, 'anna.antonova@example.com'),
(5, 'dmitry.dmitriev@example.com');