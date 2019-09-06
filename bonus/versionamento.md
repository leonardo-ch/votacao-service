# Como fazer versionamento da aplicação?
A ideia de versionamento é no caso de existir uma mudança que pode quebrar a compatibilidade com a versão atual. 
Nesse caso, é necessária, introduzir uma nova versão de API e subir o número da versão.
Assim os clientes existentes podem continuar a usar a versão antiga da API; e os clientes novos ou atualizados podem obter a nova funcionalidade na nova versão da API.

## V1 e V2
Em cada versão principal de implementação da API em um módulo separado cuja identificação é o número de versão principal (ex. v1, v2).
## Accept V1 e V2
Dentro de cada versão principal utilizar o cabeçalho Accept da requisição HTTPS para determinar o número de versão secundária e escrever código condicional para responder às versões menores em conformidade.