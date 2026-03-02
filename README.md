# AgentsVault

AgentsVault é um aplicativo Android desenvolvido em Kotlin com Jetpack Compose.

Criei o projeto para aplicar na prática conceitos que venho estudando, como consumo de API, gerenciamento de estado e construção de interfaces declarativas. 

- [Tecnologias](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Demonstração](#demonstração)
- [Tomadas de Decisões](#tomadas-de-decisões)
- [Objetivo](#objetivo-do-projeto)
- [Contato](#contato)
- [Aviso Legal](#aviso-legal)

## Tecnologias utilizadas

- [Kotlin](https://kotlinlang.org/) — Melhor linguagem de todas
- [Jetpack Compose](https://developer.android.com/jetpack/compose) — Criação rápida e simples da interface
- [Material 3](https://m3.material.io/) — Utilizado para manter consistência visual e boas práticas de UI
- Arquitetura MVVM — Organização do projeto utilizando ViewModel para gerenciamento de estado e separação de responsabilidades.
- [Retrofit](https://square.github.io/retrofit/) — Cliente HTTP para consumo da API REST.
- [Coil](https://coil-kt.github.io/coil/compose/) — Carregamento assíncrono de imagens via URL (AsyncImage)

## Funcionalidades

* Listagem de agentes  
* Exibição de imagem e background personalizado  
* Classe do agente (Duelista, Controlador, etc.)  
* Modal Bottom Sheet com habilidades  
* Ícones e descrição detalhada de cada habilidade  
* Interface moderna com gradientes personalizados  

## Demonstração
Abaixo irei disponibilizar um vídeo mostrando como o aplicativo está:
> https://github.com/user-attachments/assets/1a3037c4-6589-4985-bdc3-e37e4edf24b0

## Tomadas de Decisões

### Jetpack Compose
Optei por usar Jetpack Compose porque já vinha estudando a ferramenta e queria aplicá-la em um projeto real. A abordagem declarativa torna a construção da interface mais direta e organizada, além de deixar o código mais legível e fácil de manter.

### Arquitetura (MVVM)
Escolhi estruturar o projeto em MVVM para manter a lógica separada da interface. O uso de ViewModel ajuda no gerenciamento de estado e segue as boas práticas recomendadas no desenvolvimento Android moderno.

### Consumo de API
Os dados dos agentes são obtidos por meio de uma API pública da comunidade. Isso me permitiu praticar requisições HTTP, tratamento de dados e organização em camadas dentro do projeto.
> 🔗 Link da API: https://valorant-api.com/ 

### Carregamento de Imagens
Utilizei a biblioteca Coil para carregar imagens via URL de forma assíncrona, garantindo uma experiência mais fluida e melhor desempenho na renderização das imagens.

### Material 3
Material 3 foi escolhido para manter um visual moderno e consistente, aproveitando componentes já bem estruturados e alinhados às diretrizes atuais do Android.

## Objetivo do Projeto

Este projeto foi desenvolvido com o objetivo de:

* Praticar consumo de API REST
* Aprimorar UI com Jetpack Compose
* Trabalhar com estados e BottomSheet
* Construir portfólio 

## Contato

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)

## Aviso Legal

Este projeto foi desenvolvido exclusivamente para fins educacionais e de portfólio.

**AgentsVault** não é afiliado, patrocinado ou endossado pela Riot Games.

Valorant e todos os ativos relacionados (nomes, imagens, personagens e dados) são propriedade intelectual da Riot Games.

Os dados utilizados neste aplicativo são fornecidos por uma API Pública da Comunidade: https://valorant-api.com/
