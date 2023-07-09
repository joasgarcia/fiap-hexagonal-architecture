# fiap-hexagonal-architecture
Projeto prático desenvolvido durante a Postech FIAP (Software Architecture).

## Event Storming
https://www.figma.com/file/ad4YsYUo9lsNxWB75KuXWH/Event-Storm---Sistema-para-lanchonete?type=whiteboard&node-id=0%3A1&t=NtciJNRj0z2uN9YQ-1

## Dicionário linguagem ubiqua

- Lanchonete: Estabelecimento comercial que vende lanches e bebidas.
- Pedido: Relação de compra de um cliente na lanchonete que pode conter um ou mais itens
- Item do pedido: Compõe o pedido, pode ser um produto ou um combo de produtos.
- Cliente: Qualquer pessoa que realiza compras numa lanchonete
- Cozinha: Local da lanchonete onde se preparam os pedidos
- Cozinheiro: Profissional contratado pela lanchonete para realizar a preparação dos pedidos
- Gateway de pagamento: Empresa que oferece um meio para processamento de pagamentos. Ex: Asaas, Mercado Pago, Stripe etc.

## Documentação da API

O repositório conta com uma [collection do Postman](./FIAP%20-%20Aplicativo%20de%20Restaurante.postman_collection.json) para facilitar os testes em ambiente local.

Para iniciar o projeto, é necessário inicializar o banco:

```shell
docker compose up database --build -d
```

O banco ficará disponível na porta `3307`, a fim de evitar conflitos com outros bancos locais. Para conectar via MySQL Workbench (ou outra ferramenta), basta usar os seguintes dados:

- Host: `localhost:3307/techchallenge`
- Usuário: `root`
- Senha: `senha`

É necessário criar um Schema com o nome `techchallenge`:
```sql
CREATE SCHEMA techchallenge;
```

Por fim, basta rodar a aplicação:

```shell
docker compose up app --build
```

Isso fará a aplicação subir na porta `8080`.



