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

## Documentação da API

A documentação da API foi gerada a partir da utilização da biblioteca [Springdoc (OpenAPI)](https://springdoc.org/).

Após iniciar a aplicação, a documentação pode ser acessada através do endereço:

http://localhost:8080/swagger-ui/index.html

