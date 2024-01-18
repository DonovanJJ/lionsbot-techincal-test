-- seed roles
INSERT into roles(role_id, name, description) VALUES (1, 'ROLE_ADMIN', 'Admin role');
INSERT into roles(role_id, name, description) VALUES (2, 'ROLE_CUSTOMER', 'customer role');

---- Seed users
---- password1
--INSERT INTO customers (customer_id, username, password, email, contact) VALUES ('420d0ac8-be73-4eab-81c8-ed5680e4de8e', 'user1', 'password1', 'user1@example.com', '123456');
---- password2
--INSERT INTO customers (customer_id, username, password, email, contact) VALUES ('ee35101f-a76f-44da-9c3c-999215294b5d', 'user2', 'password2', 'user2@example.com', '789012');
---- password3
--INSERT INTO customers (customer_id, username, password, email, contact) VALUES ('ee35101f-a76f-44da-9c3c-999215294b5f', 'user3', '$2a$12$pPPRB9SHMC2.lN3wD5wQXeKINTAxjGxdYfsFx6afopQHCU1Xdpb1O', 'user3@example.com', '789012');
--
---- Assign roles to users
--INSERT INTO customer_role (customer_id, role_id) VALUES ('420d0ac8-be73-4eab-81c8-ed5680e4de8e', 1); -- user1 gets ROLE_ADMIN
--INSERT INTO customer_role (customer_id, role_id) VALUES ('ee35101f-a76f-44da-9c3c-999215294b5d', 2); -- user1 gets ROLE_CUSTOMER
--INSERT INTO customer_role (customer_id, role_id) VALUES ('ee35101f-a76f-44da-9c3c-999215294b5d', 1); -- user2 gets ROLE_ADMIN
--INSERT INTO customer_role(customer_id, role_id) VALUES ('ee35101f-a76f-44da-9c3c-999215294b5f', 2); -- user3 gets ROLE_CUSTOMER

