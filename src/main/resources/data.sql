insert into customer (id, cpf, name, email) values (1, '00000000001', 'Galeno de Melo', 'galeno@fiap-teste.com')
insert into customer (id, cpf, name, email) values (2, '00000000002', 'Joas Garcia', 'joas@fiap-teste.com')
insert into customer (id, cpf, name, email) values (3, '00000000003', 'Richard Altmayer', 'richard@fiap-teste.com')

insert into product(id, name, category, price, description) values (1, 'Snack', 'SNACK', 10.0, 'Do you see any Teletubbies in here? Do you see a slender plastic tag clipped to my shirt with my name printed on it?')
insert into product(id, name, category, price, description) values (2, 'Drink', 'DRINK', 9.9, 'Do you see a little Asian child with a blank expression on his face sitting outside on a mechanical helicopter that shakes when you put quarters in it?')
insert into product(id, name, category, price, description) values (3, 'Dessert', 'DESSERT', 5.5, 'No? Well, that is what you see at a toy store. And you must think you are in a toy store, because you are here shopping for an infant named Jeb.')

insert into image(id, product_id, src) values (1, 2, 'https://www.imigrantesbebidas.com.br/bebida/images/products/full/87195-cerveja-heineken-shot-long-neck-250ml.20230414153901.jpg')

insert into item(id) values (1)
insert into item(id) values (2)
insert into item(id) values (3)
insert into item(id) values (4)

insert into item_product(id, item_id, product_id) values (1, 1, 1)
insert into item_product(id, item_id, product_id) values (2, 2, 2)
insert into item_product(id, item_id, product_id) values (3, 3, 3)
insert into item_product(id, item_id, product_id) values (4, 4, 1)
insert into item_product(id, item_id, product_id) values (5, 4, 2)
insert into item_product(id, item_id, product_id) values (6, 4, 3)

insert into "order"(id, customer_id, date_created, status) values (1, 3, TIMESTAMP '2023-06-28 10.00.00', 'PENDING')
insert into "order"(id, date_created, status) values (2, TIMESTAMP '2023-06-28 10.00.00', 'PENDING')

insert into order_item(id, order_id, item_id) values (1, 1, 1)
insert into order_item(id, order_id, item_id) values (2, 1, 2)
insert into order_item(id, order_id, item_id) values (3, 1, 3)
insert into order_item(id, order_id, item_id) values (4, 2, 1)
insert into order_item(id, order_id, item_id) values (5, 2, 3)


