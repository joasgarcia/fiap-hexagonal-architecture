# fiap-hexagonal-architecture
Projeto prático desenvolvido durante a Postech FIAP (Software Architecture).

[Link para o repositório do Github](https://github.com/richardaltmayer/fiap-hexagonal-architecture)


## Evento Storming
https://www.figma.com/file/ad4YsYUo9lsNxWB75KuXWH/Event-Storm---Sistema-para-lanchonete?type=whiteboard&node-id=0%3A1&t=NtciJNRj0z2uN9YQ-1

## Dicionário linguagem ubiqua

- Lanchonete: Estabelecimento comercial que vende lanches e bebidas.
- Pedido: Relação de compra de um cliente na lanchonete que pode conter um ou mais itens
- Item do pedido: Compõe o pedido, pode ser um produto ou um combo de produtos.
- Cliente: Qualquer pessoa que realiza compras numa lanchonete
- Cozinha: Local da lanchonete onde se preparam os pedidos
- Cozinheiro: Profissional contratado pela lanchonete para realizar a preparação dos pedidos
- Gateway de pagamento: Empresa que oferece um meio para processamento de pagamentos. Ex: Asaas, Mercado Pago, Stripe etc.

## Collection da API (Postman)

A lista de endpoints com o payload já configurado basta baixar a [_collection_](./fiap-techchallenge.postman_collection.json) e importá-la no Postman. 

## Como rodar a aplicação

Primeiro, é necessário subir o banco de dados em MySQL:

```shell
kubectl apply -f k8s-mysql.yaml;
```

Em seguida, é possível subir os pods da aplicação Spring Boot:

```shell
kubectl apply -f k8s-app.yaml;
```

É necessário esperar cerca de 30s até a aplicação fazer o _build_ e rodar. Para acompanhar o progresso, basta executar um `kubectl logs` em algum dos pods e verificar se o log `Started RestaurantApplication...` apareceu.

Para usar a _collection_ com `localhost`, é preciso mapear as requisições na porta 8080.

```shell
kubectl port-forward service/app-restaurant-service 8080:8080;
```

## Como rodar os testes da aplicação

### Via linha de comando
Dentro do diretório do projeto, executar o comando abaixo:

```shell
.\gradlew test
```

Após a execução do comando, é possível visualizar o relatório de execução dos testes, no formato HTML, localizado em:

```shell
> .\build\reports\tests\test\index.html
```

## Cobertura de testes do projeto

Os testes foram construídos, nas diferentes camadas do projeto, utilizando:
- Testes unitários
- Testes de integração
- Testes de comportamento (BDD)

O resumo dos testes de integração e unitários pode ser observado em:
![tests-summary](https://github.com/richardaltmayer/fiap-hexagonal-architecture/assets/10313123/43658221-1023-4f6d-98c9-9f84713e1e4b)

_Os testes de comportamento não constam no resumo acima._

A cobertura de testes desenvolvidos no projeto pode ser observada em:
![tests-coverage](https://github.com/richardaltmayer/fiap-hexagonal-architecture/assets/10313123/e70ba972-024b-4e2b-b5ad-c750409ea653)

## Relatório de Impacto de Proteção de Dados

Para visualizar o Relatório de Impacto de Proteção de Dados consulte esse [link](https://docs.google.com/document/d/1qFt32F8fH0thY0iOhRW_RAAAZw-RYRzcKtixA6_6wHQ/edit?usp=sharing).

## OWASP ZAP (ZAP Scanning Reports)

Os relatórios obtidos a partir da ferramenta mostram que os endpoints abaixo não apresentam vulunerabilidades altas, não sendo necessário realizar adequações na implementação, conforme:
- [Product List](./src/main/resources/owasp/product-list.html)
- [Checkout](./src/main/resources/owasp/checkout.html)
- [Payment](./src/main/resources/owasp/payment.html)
- [Payment Confirmation (Webhook)](./src/main/resources/owasp/webhook-payment-status.html)