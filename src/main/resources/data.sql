insert into customer (id, cpf, name) values (1, '00000000001', 'Galeno de Melo')
insert into customer (id, cpf, name) values (2, '00000000002', 'Joas Garcia')
insert into customer (id, cpf, name) values (3, '00000000003', 'Richard Altmayer')

insert into item(id, name) values (1, 'Product 1')
insert into "order"(id, customer_id, date_created) values (1, 3, TIMESTAMP '2023-06-28 10.00.00')
insert into order_item(id, order_id, item_id) values (1, 1, 1)