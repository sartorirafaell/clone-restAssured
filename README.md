🚀 **Automação de Testes de API com Rest Assured**

Projeto de automação de testes de API REST utilizando Java + Rest Assured, estruturado em arquitetura em camadas para garantir organização, reutilização e fácil manutenção.

📂 **Estrutura do Projeto**
    src
     ├── main
     │    └── java/com/vemser/rest
     │         ├── client     # Camada de serviços: chamadas às APIs
     │         ├── data       # Massa de dados para os testes
     │         ├── model      # Modelos (POJOs) de request/response
     │         ├── utils      # Utilitários e configurações globais
     │         └── resources  # Arquivos externos (ex.: configs, schemas)
     │
     └── test                 # Casos de teste


🏗️ **Arquitetura em Camadas**

Client → Contém os métodos que fazem as requisições HTTP (Service Layer).

Data → Armazena dados de apoio, como massas de teste estáticas ou builders.

Model → Define os objetos (POJOs) para request e response da API.

Utils → Funções auxiliares e configurações compartilhadas (ex.: BaseConfig).

Resources → Arquivos externos como .properties, JSONs e schemas.

Test → Camada de execução dos testes, que valida as respostas das APIs.
