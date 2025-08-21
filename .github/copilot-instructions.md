# Projeto Sudoku POO - Instruções para Agente de IA

Este documento fornece orientação para agentes de codificação de IA que trabalham no projeto Sudoku POO (Programação Orientada a Objetos).

## Visão Geral da Arquitetura

Este é um jogo de Sudoku baseado em linha de comando escrito em Java. A arquitetura é simples e segue princípios básicos de orientação a objetos.

- **`br.com.dio.Main`**: O ponto de entrada da aplicação. Esta classe é responsável pelo loop principal do jogo, tratando a entrada do usuário e exibindo o tabuleiro do jogo.
- **`br.com.dio.model`**: Este pacote contém a lógica principal e as estruturas de dados do jogo.
  - **`Board.java`**: A classe central que representa o tabuleiro do Sudoku. Ela gerencia a grade de objetos `Space`, verifica o status do jogo (`GameStatusEnum`) e valida o tabuleiro em busca de erros.
  - **`Space.java`**: Representa uma única célula no tabuleiro. Contém o valor `expected` (o número correto para a célula) e o valor `actual` (a entrada do usuário). Também possui uma propriedade `fixed` para indicar números que fazem parte do quebra-cabeça inicial.
  - **`GameStatusEnum.java`**: Um enum que define o estado do jogo (`NON_STARTED`, `INCOMPLETE`, `COMPLETE`).
- **`br.com.dio.util`**: Este pacote contém classes utilitárias.
  - **`BoardTemplate.java`**: Fornece um modelo de string estático para renderizar o tabuleiro do Sudoku no console.

O fluxo de dados é direto: A classe `Main` instanciará um `Board` e, em seguida, entrará em um loop para aceitar comandos do usuário para modificar o tabuleiro. Após cada jogada, ela deve atualizar a exibição usando o `BoardTemplate`.

## Fluxo de Trabalho do Desenvolvedor

### Compilando o Projeto

O projeto é compilado usando o compilador Java padrão (`javac`). Os arquivos-fonte estão em `src/` e os arquivos `.class` compilados devem ser colocados em `bin/`.

Para compilar todos os arquivos Java a partir da raiz do projeto:
```sh
javac -d bin -sourcepath src src/br/com/dio/**/*.java
```

### Executando a Aplicação

O jogo é executado a partir da linha de comando usando o comando `java`.

Para executar a aplicação principal a partir da raiz do projeto:
```sh
java -cp bin br.com.dio.Main
```

### Principais Padrões de Implementação

- **Representação do Tabuleiro**: O tabuleiro é uma `List<List<Space>>`. A lista externa representa as colunas e a lista interna representa as linhas. Acesse um espaço usando `board.getSpaces().get(col).get(row)`.
- **Gerenciamento de Estado**: A classe `Board` gerencia o estado do jogo através do método `getStatus()`, que usa Java Streams para verificar eficientemente se o tabuleiro está completo ou ainda em andamento.
- **Interação com o Usuário**: O loop do jogo em `Main.java` deve ser implementado para:
  1. Criar uma instância de `Board`.
  2. Imprimir continuamente o tabuleiro usando `BoardTemplate`.
  3. Solicitar a entrada do usuário (por exemplo, coluna, linha e valor).
  4. Atualizar o `Board` com a entrada do usuário usando `board.changeValue(col, row, value)`.
  5. Verificar a conclusão do jogo usando `board.gameIsFinished()`.
