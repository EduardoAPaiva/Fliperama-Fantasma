# 🎮 Fliperama Fantasma

Projeto desenvolvido para a disciplina **SCC0604 – Programação Orientada a Objetos** da Universidade de São Paulo, campus São Carlos, unidades EESC & ICMC.

## 📖 Sobre o projeto

O *Fliperama Fantasma* é um jogo implementado em Java com foco na aplicação de conceitos de orientação a objetos, como encapsulamento, modularização e organização em pacotes.

O jogo apresenta uma **gameplay variada**, com diferentes mecânicas em cada fase.
A história acompanha um garoto que encontra um fliperama em uma cabana abandonada e descobre que ele está amaldiçoado. Para se libertar, ele precisa vencer todos os minigames impostos pelo fantasma que habita a máquina.

Além disso, recomenda-se fortemente jogar **com som ativado**, pois todas as fases possuem **músicas autorais** elaboradas pelos próprios autores, contribuindo diretamente para a imersão do jogo e da compreensão da história, uma vez que as músicas revelam os rumos da trama.

## 📁 Estrutura de pastas

```bash
Fliperama-Fantasma/
├── imgs/                 # Imagens utilizadas no jogo
└── src/                  # Código-fonte em Java
    ├── Main.java
    ├── Controler/        # Controle geral do fluxo do jogo
    ├── Modelo/           # Estruturas e entidades do jogo
    ├── Auxiliar/         # Classes auxiliares/utilitárias
    ├── FaseMenu/         # Tela inicial/menu
    ├── FasePacman/       # Fase inspirada em Pac-Man
    ├── FaseCobrinha/     # Fase estilo Snake
    ├── FaseCabana/       # Fase temática de introdução (cabana)
    ├── FaseShotDuck/     # Fase estilo Duck Hunt
    └── FaseExplorador/   # Fase de exploração
```

## 🎮 Fases do jogo

* **FaseCabana**
  Introdução da história. O jogador explora a cabana abandonada e encontra o fliperama amaldiçoado.

* **FasePacman**
  Inspirada em jogos clássicos de labirinto, onde o jogador deve coletar itens enquanto evita inimigos.

* **FaseCobrinha**
  Baseada no estilo *Snake*, o jogador controla um personagem que cresce ao coletar itens, evitando colisões.

* **FaseShotDuck**
  Inspirada em jogos de tiro. Nesta fase, o jogador utiliza o **mouse para puxar o estilingue e realizar disparos** contra os alvos.

* **FaseExplorador**
  Fase com foco em exploração, incentivando o jogador a navegar pelo ambiente e descobrir caminhos.

* **FaseMenu**
  Interface inicial para navegação e início do jogo.

## 🎮 Controles

* **W, A, S, D** → Movimentação do personagem
* **ESC** → Fecha o jogo *(exceto no menu)*
* **J** → Retrocede uma fase
* **K** → Avança para a próxima fase
* **Mouse (FaseShotDuck)** → Puxar o estilingue e atirar

## ▶️ Como executar

A partir da pasta raiz do projeto, execute:

```bash
java src/Main.java
```

Certifique-se de ter o Java instalado e configurado no seu sistema.

## 👥 Autores

* **Eduardo Alves Paiva**

  * Fase Cobrinha
  * Fase inicial
  * Músicas de todas as fases
  * Menu inicial
  * Mecânica de movimentação fluida

* **Henrique Ribeiro de Figueiredo**

  * Fase Pacman
  * Músicas de todas as fases

* **João Pedro Biazus Fagá**

  * Fase Explorador
  * Imagem dos créditos
  * Música da fase inicial

* **Pedro Camelo Gonzaga**

  * Fase ShotDuck (atire no pato)
  * Música da fase inicial
  * Música da fase do Pacman
