package br.com.dio;

import br.com.dio.model.Board;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    
        private final static Scanner scanner = new Scanner(System.in);

        private static Board board;

        private final static int BOARD_LIMIT = 9;

        public static void main (String[] args){
            final var positions = Stream.of(args)
            .collect(toMap(
                 k -> k.split(";")[0],
                 v -> v.split( ";")[0]
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

        private static void StartGame(final Map<String, String > positions) {
            if (nonNull(board)){
                System.out.println("O jogo ja foi iniciado");
                return;
            }

            List<List<Space>> spaces = new ArrayList<>();
            for (int i = 0; i < BOARD_LIMIT ; i++){
                spaces.add(new ArrayList<>());
                for (int j = 0; j < BOARD_LIMIT ;j++){
                    var positionConfig = positions.get("%s,%s".formatted(i, j));
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

        private static Object removeNumber() {
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
            if (!board.clearValue(col, row)){
                System.out.printf("A posicao [%s,%s] tem um valor fixo \n", col, row);
            }
        }

        private static Object finishGame() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'finishGame'");
        }

        private static Object clearGame() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clearGame'");
        }

        private static Object showGameStatus() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'showGameStatus'");
        }

        private static Object showCurrentGame() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'showCurrentGame'");
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
