

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;


public class Main {
    
        private final static Scanner scanner = new Scanner(System.in);

        private static Board board;

        private final static int BOARD_LIMIT = 9;

        public static void main (String[] args){
            final var positions = Stream.of(args)
    .collect(toMap(
        k -> k.split(";")[0], // chave = "0,0"
        v -> v.split(";")[1]  // valor = "5,true"
    ));

        var option = -1;
        while(true){
            System.out.println("Selecione uma das opcoes a seguir: ");
            System.out.println("1 - Inicia um novo jogo");
            System.out.println("2 - Insere um novo numero");
            System.out.println("3 - Remover um numero");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();
            
            switch (option){
                case 1 -> StartGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opcao invalida, selecione uma opcao do menu");
            }
        }

        }

        private static void StartGame(Map<String, String> positions) {
    if (nonNull(board)) {
        System.out.println("O jogo ja foi iniciado");
        return;
    }
    if (positions.isEmpty()) {
        positions = Map.ofEntries(
            Map.entry("0,0", "5,true"), Map.entry("1,0", "3,true"), Map.entry("4,0", "7,true"),
Map.entry("0,1", "6,true"), Map.entry("3,1", "1,true"), Map.entry("4,1", "9,true"), Map.entry("5,1", "5,true"),
Map.entry("1,2", "9,true"), Map.entry("2,2", "8,true"), Map.entry("7,2", "6,true"),
Map.entry("0,3", "8,true"), Map.entry("4,3", "6,true"), Map.entry("8,3", "3,true"),
Map.entry("0,4", "4,true"), Map.entry("3,4", "8,true"), Map.entry("5,4", "3,true"), Map.entry("8,4", "1,true"),
Map.entry("0,5", "7,true"), Map.entry("4,5", "2,true"), Map.entry("8,5", "6,true"),
Map.entry("1,6", "6,true"), Map.entry("6,6", "2,true"), Map.entry("7,6", "8,true"),
Map.entry("3,7", "4,true"), Map.entry("4,7", "1,true"), Map.entry("5,7", "9,true"), Map.entry("8,7", "5,true"),
Map.entry("4,8", "8,true"), Map.entry("7,8", "7,true"), Map.entry("8,8", "9,true")
        );
    }

    List<List<Space>> spaces = new ArrayList<>();
    for (int i = 0; i < BOARD_LIMIT; i++) {
        spaces.add(new ArrayList<>());
        for (int j = 0; j < BOARD_LIMIT; j++) {
            String positionKey = String.format("%s,%s", i, j);
            String positionConfig = positions.getOrDefault(positionKey, "0,false"); // Valor padrão caso a chave não exista
            
            var expected = Integer.parseInt(positionConfig.split(",")[0]);
            var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
            var currentSpace = new Space(expected, fixed);
            spaces.get(i).add(currentSpace);
        }
    }

    board = new Board(spaces);
    System.out.println("O jogo pronto para comecar");
}

        private static void inputNumber() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }

            System.out.println("Informe a coluna: ");
            var col = runUntilGetValidNumber(0, 8);
            System.out.println("Informe a linha: ");
            var row = runUntilGetValidNumber(0, 8);
            System.out.printf("Infome o numero que vai entrar na posicao [%s,%s]\n", col, row);
            var value = runUntilGetValidNumber(1, 9);
            if (!board.changeValue(col, row, value)){
                System.out.printf("A posicao [%s,%s] tem um valor fixo \n", col, row);
            }
        }

        private static void removeNumber() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }

            System.out.println("Informe a coluna: ");
            var col = runUntilGetValidNumber(0, 8);
            System.out.println("Informe a linha: ");
            var row = runUntilGetValidNumber(0, 8);
            if (!board.clearValue(col, row)){
                System.out.printf("A posicao [%s,%s] tem um valor fixo \n", col, row);
            }
        }

        private static void showCurrentGame() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }

            var args = new Object[81];
            var argPos = 0;
            for (int i = 0; i < BOARD_LIMIT; i++) {
                for (var col: board.getSpaces()){
                    args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
                }
            }
            System.out.println("SEU JOGO: ");
            System.out.printf((BOARD_TEMPLATE)+ "\n", args);
        }

        private static void  showGameStatus() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }
            System.out.printf("Status do seu jogo: %s \n", board.getStatus().getLabel());
            if (board.hasErrors()){
                System.out.println("O jogo contem erros");
            }else{
                System.out.println("Sem erros");
            }
        }

        private static void clearGame() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }

            System.out.println("Deseja limpar jogo? [sim/nao]");
            var confirm = scanner.next();
            while(!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("nao")){
                System.out.println("Informe apenas 'sim' ou 'nao'");
                confirm = scanner.next();
            }

            if (confirm.equalsIgnoreCase("sim")){
                board.reset();
                return;
            }
        }

        private static void finishGame() {
            if (isNull(board)){
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }

            if (board.gameIsFinished()){
                System.out.println("Parabens! Voce concluiu o jogo");
                showCurrentGame();
                board = null;
            }
            else if (board.hasErrors()){
                System.out.println("Seu jogo contem erros, verifique para acabar!");
            }
            else{
                System.out.println("Ainda ha espacos vazios");
            }
        }

        

        

        

        

        private static int runUntilGetValidNumber(final int min, final int max){
            var current = scanner.nextInt();
            while (current < min || current > max || current % 1 != 0 ){
                System.out.printf("Informe um numero entre %s e %s\n", min, max);
                current = scanner.nextInt();
            }
            return current;
        }
        
}
