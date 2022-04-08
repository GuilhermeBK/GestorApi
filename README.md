# GestorApi

<p align="center">
<img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge"/>
</p>

Software pensando para gestao de finanças pessoais. Feito em 2019/2020 em spring 2.1.x 
Estou atualizando para 2.5.0, principalmente questoes de segurança.


# Tecnologias usadas
- ``Spring boot 2.5.0 ``
- `` Java 11 ``
- `` JPA ``
- `` Criteria do JPA ``
- `` Hibernate ``
- `` Deploy no heroku, bd jawsbd mysql ``
- `` JWT ``
- `` OAuth2 ``
- `` Relatorios em PDF com Jaspersoft ``

# Necessario para rodar
Java 11

## :hammer: endpoints do projeto

* [**Categorias**](#) http://localhost:8080/categorias/
* [**Pessoas**](#) http://localhost:8080/pessoas
* [**Lancamentos**](#) http://localhost:8080/lancamento

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |

## Respostas

| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success).|
| `201` | Requisição executada e criada com sucesso (Created).|
| `400` | Erros de validação ou os campos informados não existem no sistema.|
| `401` | Dados de acesso inválidos/não autorizado.|
| `404` | Registro pesquisado não encontrado (Not found).|
| `405` | Método não implementado.|
| `410` | Registro pesquisado foi apagado do sistema e não esta mais disponível.|
| `422` | Dados informados estão fora do escopo definido para o campo.|

## Listar
As ações de `listar` do Lançamento permitem o envio dos seguintes parâmetros:

| Parâmetro | Descrição |
|---|---|
| `/estatisticas/por-dia` | Filtra dados de lançamentos com o dia atual. |
| `/estatisticas/por-categoria` | Filtra dados de lançamentos por categorias. |
| `resumo ` | Filtra dados de lançamentos resumidos. |
| `page` | Informa qual página deve ser retornada. |

# Group Autenticação - OAuth

API utiliza [OAuth2](https://oauth.net/2/) como forma de autenticação/autorização.

## Solicitando tokens de acesso [http://localhost:8080/oauth/token]

### Utilizando o código de acesso [POST]

#### Autorização maxima

| Parâmetro | Descrição |
|---|---|
| `client` | `angular` |
| `username` | `adm@gmail.com`. |
| `password` | `admin`. |
| `grant_type` | `password`. |

+ Request (application/json)

    + Headers

            Authorization: Basic YW5ndWxhcjpAbmd1bEByMA==

+ Response 200 (application/json)

    + Body

    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1AZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sIm5vbWUiOiJBZ",
        "token_type": "bearer",
        "expires_in": 1799,
        "scope": "read write",
        "nome": "Administrador",
        "jti": "OzkTf-epfmSchYx8ygJdTAzRXVY"
    }
    

#### Autorização apenas de leitura

| Parâmetro | Descrição |
|---|---|
| `client` | `angular` |
| `username` | `maria@gmail.com`. |
| `password` | `maria`. |
| `grant_type` | `password`. |

+ Request (application/json)

    + Headers

            Authorization: Basic YW5ndWxhcjpAbmd1bEByMA==

+ Response 200 (application/json)

    + Body
    
      {

          "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJtYXJpYUBnbWFpbC5jb",
          "token_type": "bearer",
          "expires_in": 1799,
          "scope": "read write",
          "nome": "Maria Silva",
          "jti": "1zwC4gcSjrWQxZGNIeCt5ti5bzQ"

      }
      
#### Removendo Token do Cookie do usuario (Logout)

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

      
      
     
# Group Recursos

# Categorias [/categorias]

As categorias são como seus ativos e passivos são divididos

### Novo (Create) [POST]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]
            
    + Body

          {
              "nome" : "categoria"
          }
            
     
 # Pessoas [/Pessoas]
 
 Pessoas serão as responsaveis pelos creditos e debitos (apenas Pessoas ATIVAS podem fazer lançamentos).
 
### Novo (Create) [POST]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]
            
    + Body

          {
              "nome": "guilherme",
              "endereco" : {
                  "logradouro": "Rua sapiranga",
                  "numero" : "556",
                  "bairro" : "sao luiz",
                  "cep" : "93806332",
                  "cidade" : "sao paulo",
                  "estado" : "RS" 
              },
              "ativo" : true
          }


### Atualizar cadastro de pessoa [PUT  http://localhost:8080/pessoas/{ID}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]
            
    + Body

          {
                  "nome": "gui",
                  "ativo": true,
                  "endereco": {
                      "logradouro": "Rua tal",
                      "numero": "556",
                      "complemento": "complemento",
                      "bairro": "sao luiz",
                      "cep": "93806332",
                      "cidade": "Sapiranga",
                      "estado": "RS"
                  }
           }

### Atualizar ativo de pessoa [PUT http://localhost:8080/pessoas/{ID}/ativo]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]
            
    + Body

        {
        "ativo": "true" 
        }

### Busca pessoa por nome e ou iniciais do nome [GET http://localhost:8080/pessoas?nome={nome}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Params
              nome: Gui 
              
           
          
# Lançamentos [/lancamentos]

Lancamentos de ativos e passivos

### Busca por uma descricao especifica [GET http://localhost:8080/lancamento?]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
        + Params
             
                    descricao: salario 

### Busca apartir uma data de vencimento especifica [GET http://localhost:8080/lancamento?dataVencimentoDe=]
        

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
        + Params
             
                    dataVencimentoDe: 12/12/2016 
                    
                    
### Busca entre duas datas de vencimento [GET http://localhost:8080/lancamento?dataVencimentoDe={data}&dataVencimentoAte={data}]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
        + Params
             
                    dataVencimentoDe: 12/12/2016, 
                    dataVencimentoAte: 10/06/2017
                    
                 
### Busca até uma data de vencimento especifica [GEThttp://localhost:8080/lancamento?dataVencimentoAte={data}]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
        + Params
             
                    dataVencimentoAte: 10/06/2017

### Busca os lancamentos e agrupa por tipo, dia e total do mes ATUAL [GET http://localhost:8080/lancamento/estatisticas/por-dia]
+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
       
### Busca os lancamentos e agrupa por categoria [GET http://localhost:8080/lancamento/estatisticas/por-categoria]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]

### Novo (Create) [POST http://localhost:8080/lancamento/]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
            +Body
                  {
                      "descricao": "CAIXA",
                      "dataVencimento" : "28/03/2022",
                      "valor": 1000,
                      "tipo" : "DESPESA",
                      "categoria":{
                          "codigo": 2
                      },
                      "pessoa" : {
                          "codigo" : 1
                      }

                  }
                  
### Atualizar [PUT http://localhost:8080/lancamento/{ID DO LANCAMENTO}]

+ Request (application/json)

   + Headers

            Authorization: Bearer [access_token]
            
            +Body
            
              {
                "descricao": "CAIXA 2",
                "dataVencimento" : "12/12/2022",
                "valor": 1000,
                "tipo" : "RECEITA",
                "categoria":{
                    "codigo": 2
                },
                "pessoa" : {
                    "codigo" : 1
                }

              }
