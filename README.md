# GestorApi

<p align="center">
<img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge"/>
</p>

Software pensando para gestao de finanças pessoais. Feito em 2019/2020 em spring 2.1.x 
Estou atualizando para 2.5.0, principalmente questoes de segurança.


# Tecnologias usadas
Spring boot 2.5.0
Java 11
JPA
Criteria do JPA
Hibernate
Deploy no heroku, bd jawsbd mysql
JWT
OAuth2
Relatorios em PDF com Jaspersoft

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
| Parâmetro | Descrição |
|---|---|
| `client` | `angular` |
| `username` | `adm@gmail.com`. |
| `password` | `admin`. |
| `grant_type` | `password`. |








