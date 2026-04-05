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
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) — Persistência de dados local utilizada para salvar agentes favoritados.
- [Splash Screen](https://developer.android.com/develop/ui/views/launch/splash-screen) — Tela de abertura com logo e fundo para melhorar a experiência inicial do app.
- [Navigation](https://developer.android.com/jetpack/compose/navigation) — Gerenciamento de navegação entre telas.

## Funcionalidades

* Listagem de agentes  
* Busca de agentes  
* Favoritar agentes com persistência usando DataStore
* Suporte a temas (Light, Dark e System) com persistência usando DataStore  
* Tela dedicada para visualizar agentes favoritados  
* Splash Screen inicial do aplicativo 
* Seleção de agentes com indicador visual no card
* Exibição de imagem e background personalizado  
* Classe do agente (Duelista, Controlador, etc.)  
* Modal Bottom Sheet com habilidades  
* Ícones e descrição detalhada de cada habilidade  
* Interface moderna com gradientes personalizados  

## Demonstração
Abaixo irei disponibilizar um vídeo mostrando como o aplicativo está:
> https://github.com/user-attachments/assets/63b15537-f3a6-40d7-a5a9-55baf17a6009

## Tomadas de Decisões

### Jetpack Compose
Optei por Jetpack Compose por já ter experiência com a abordagem declarativa, que torna a construção da interface mais organizada. Confesso que não sinto falta do XML

### Arquitetura (MVVM)
Escolhi estruturar o projeto em MVVM para manter a lógica separada da interface. O uso de ViewModel ajuda no gerenciamento de estado e segue as boas práticas recomendadas no desenvolvimento Android moderno.

### Consumo de API
Os dados dos agentes são obtidos por meio de uma API pública da comunidade. Isso me permitiu praticar requisições HTTP, tratamento de dados e organização em camadas dentro do projeto.
> 🔗 Link da API: https://valorant-api.com/ 

### Carregamento de Imagens
Utilizei a biblioteca Coil para carregar imagens via URL de forma assíncrona, garantindo uma experiência mais fluida e melhor desempenho na renderização das imagens.

### Material 3
Material 3 foi escolhido para manter um visual moderno e consistente, aproveitando componentes já bem estruturados e alinhados às diretrizes atuais do Android.

### Persistência de Favoritos (DataStore)
Para permitir que os agentes favoritados fossem mantidos mesmo após fechar o aplicativo, utilizei DataStore como solução de persistência local. 

### Sistema de Temas (Light, Dark e System)
Implementei um sistema de temas com suporte aos modos **Light**, **Dark** e **System**, permitindo que o aplicativo se adapte automaticamente às preferências.

### Sistema de Busca (SearchBar)
Para facilitar a navegação entre os agentes, implementei uma funcionalidade de busca que permite filtrar a lista dinamicamente conforme o nome digitado.

### Seleção de Agentes
Adicionei um indicador visual no card do agente quando ele é selecionado, facilitando o entendimento visual de qual agente está selecionado.

### Splash Screen
Implementei uma Splash Screen para criar uma experiência inicial mais polida ao abrir o aplicativo.

### Navegação entre Telas (Navigation)
Para organizar a navegação entre as telas, utilizei o Navigation. Essa biblioteca facilita muito a definição de rotas e o gerenciamento da navegação.

## Objetivo do Projeto

Este projeto foi desenvolvido com o objetivo de:

* Colocar em prática o consumo de API REST e o tratamento dos dados recebidos
* Evoluir na construção de interfaces usando Jetpack Compose
* Trabalhar melhor com gerenciamento de estado utilizando ViewModel
* Explorar interações de UI como BottomSheet
* Utilizar DataStore para salvar dados localmente
* Aplicar boas práticas de organização e arquitetura (MVVM e feature-first)
* Construir portfólio 

## Contato

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)

## Aviso Legal

Este projeto foi desenvolvido exclusivamente para fins educacionais e de portfólio.

**AgentsVault** não é afiliado, patrocinado ou endossado pela Riot Games.

Valorant e todos os ativos relacionados (nomes, imagens, personagens e dados) são propriedade intelectual da Riot Games.

Os dados utilizados neste aplicativo são fornecidos por uma API Pública da Comunidade: https://valorant-api.com/
