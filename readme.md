# Trabalho de projeto orientado a objetos

## Projeto de um sistema de consulta de notícias do IBGE

## Funcionamento do projeto

### 1. Entrypoint
O entrypoint do projeto e no Arquivo App.java, onde no construtor eu inicializo as 
dependências do projeto, como meus Repositories, Bibliotecas externas, 
Controllers e Adapters. No método Run eu inicializo as notícias pedindo para o usuário
se ele quer usar as notícias locais ou remotas. Caso o usuário escolha as notícias locais,
eu busco elas de um arquivo .json na pasta Data, caso contrário eu busco as notícias da
api do IBGE e gravo esse JSON no mesmo arquivo .json. Na sequência eu crio o User
pedindo para usuário seu nome e nick. E por último eu chamo o menu, o menu funciona de forma
recursiva e ele irá chamar os métodos do NoticeController para executar o fluxo do app

### 2. Execução da lógica para as opções do menu
No NoticeController se concentra a lógica de negócio do projeto, onde eu tenho os métodos
que o menu irá utilizar para executar as opções do usuário

### 3. Funcionamento da integração com a API do IBGE
No meu arquivo IbgeNoticeAdapter é onde ele faz essa comunicação, ele prepara a requisição
transforma o JSON em uma lista genérica e encaminha isso para o NoticeMapper, o NoticeMapper
fica responsável por pegar a Lista genérica e transformar em uma Lista da minha entidade Notice

### 4. Modelagem das entidades
As entidades do projeto estão na pasta Model, eu criei entidades para todos os possíveis dados da api.
Os dados que vinham em listas eu criei Classes para eles e na Notice defini uma lista dessa classe

