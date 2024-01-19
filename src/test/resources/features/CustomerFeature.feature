# language: pt
Funcionalidade: API - Clientes

  Cenário: Registrar um novo cliente
    Quando registrar um novo cliente
    Então o cliente é registrado com sucesso

  Cenário: Buscar um cliente que já existe por CPF
    Dado que o cliente já foi registrado
    Quando realizar uma busca por CPF
    Então o cliente é exibido
