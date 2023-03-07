package ui;

import java.util.Scanner;
import model.Board;
import model.Player;
import model.Square;

public class Main {

    public static final Scanner sc = new Scanner(System.in);
    public static final Board board = new Board();

    public static void main(String[] args) {
        Main manager = new Main();
        manager.showMainMenu();
    }

    public void showMainMenu() {
        System.out.print("WELCOME TO SNAKES AND LADDERS");
        boolean stopFlag = false;

        while (!stopFlag) {
            System.out.println("\n************************************************");
            System.out.print("MAIN MENU:"
                    + "\n[1] Play"
                    + "\n[2] Exit");
            System.out.print("\nSelect an option: ");
            int mainOption = sc.nextInt();
            System.out.println("************************************************");
            switch (mainOption) {
                case 1:
                    playGame();
                    showGameMenu();
                    break;
                case 2:
                    stopFlag = true;
                    System.out.println("Exit successfull");
                    break;
                default:
                    System.out.println("Option not available");
                    break;
            }
        }
    }

    public void showGameMenu() {
        boolean stopFlag = false;

        while (!stopFlag) {
            String message = manageTurn(board.manageTurn()); //Llamada al método manage turn de la clase Board que le envía el turno a la clase manage turn de la clase Main
            System.out.print( message // Imprime turno
                    + "\n[1] Throw dice"
                    + "\n[2] Watch ladders and snakes");
            System.out.print("\nSelect an option: ");
            int mainOption = sc.nextInt();
            switch (mainOption) {
                case 1:
                    int squaresToMove = board.throwDice();
                    System.out.println("Move " + squaresToMove + " squares.");
                    int turn = board.manageTurn();
                    Player player = board.manageTurnPlayer(turn);
                    Square current = board.searchPlayerSquare(player);
                    Square square2Move = board.calculateSquares2Move(current, squaresToMove);
                    boolean flag = board.movePlayer(current, square2Move, player);
                    if(flag == true){ //Se valida si el jugador llegó a la última casilla
                        System.out.println("Se movió"); //HAY QUE CORREGIR ESTO!!!
                    }else {
                        System.out.println("Se movió");
                    }
                    board.showBoard();
                    stopFlag = true;
                    break;
                case 2:

                    break;
                default:
                    System.out.println("Option not available");
                    break;
            }
        }
    }

    public String manageTurn(int turn){
        String message = "";
        if(turn == 1){
            message = "Player # , it's your turn.";
        }else if(turn == 2){
            message = "Player % , it's your turn.";
        }else {
            message = "Player $ , it's your turn.";
        }
        return message;
    }

    private static void playGame() {
        System.out.print("Enter the number of rows on the board: ");
        int rows = sc.nextInt();
        board.setRows(rows);
        System.out.print("Enter the number of columns on the board: ");
        int columns = sc.nextInt();
        board.setColumns(columns);
        board.setMeasures(rows*columns);
        System.out.print("Enter the number of snakes: ");
        int snakes = sc.nextInt();
        board.setSnakes(snakes);
        System.out.print("Enter the number of ladders: ");
        int ladders = sc.nextInt();
        board.setLadders(ladders);
        System.out.println("************************************************");
        int measures = columns * rows; // Medidas del tablero
        board.setMeasures(measures);
        for (int i = 0; i < measures; i++) { // Añadir un nodo al tablero
            board.addSquare(i , measures);
        }
        board.addPlayers(); // Agrega los 3 jugadores
        board.showBoard();
        System.out.println();
        for (int i = 0; i < snakes; i++) { // Añadir una serpiente al tablero
            board.generateSnakes();
            board.setS(1);
        }
        for (int i = 0; i < ladders; i++) { // Añadir una serpiente al tablero
            board.generateLadders();
            board.setL(1);
        }
        board.showSnakesAndLadders();
    }


    private static String chronometer(int maxMin) {
        int min = 0;
        int seconds = 0;
        for (min = 0; min < maxMin; min++) {
            for (seconds = 0; seconds < 60; seconds++) {
                System.out.println(min + ":" + seconds);
                delaySeconds();
            }
        }

        String time = min + ":" + seconds;

        return time;
    }

    private static void delaySeconds() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

}

