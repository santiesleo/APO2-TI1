package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Board;

public class Main {

    public static final Scanner sc = new Scanner(System.in);
    public static final Board board = new Board();

    public static void main(String[] args) {
        Main manager = new Main();
        manager.showMainMenu();
    }

    public void showMainMenu() {
        System.out.print("BIENVENIDO A SNAKES AND LADDERS");
        boolean stopFlag = false;

        while (!stopFlag) {
            System.out.println("\n************************************************");
            System.out.print("MENÚ PRINCIPAL:"
                    + "\n[1] Jugar"
                    + "\n[2] Salir");
            System.out.print("\nSelect an option: ");
            int mainOption = sc.nextInt();
            System.out.println("************************************************");
            switch (mainOption) {
                case 1:
                    playGame();
                    break;
                case 2:
                    stopFlag = true;
                    System.out.println("Salida exitosa");
                    break;
                default:
                    System.out.println("Tú opción no se encuentra disponible");
                    break;
            }
        }
    }

    public void showGameMenu() {
        boolean stopFlag = false;

        while (!stopFlag) {
            System.out.print("Jugador " + "" + ", es tu turno"
                    + "\n[1] Tirar dado"
                    + "\n[2] Ver escaleras y serpientes");
            System.out.print("\nSelect an option: ");
            int mainOption = sc.nextInt();
            switch (mainOption) {
                case 1:
                    stopFlag = true;
                    break;
                case 2:

                    break;
                default:
                    System.out.println("Tú opción no se encuentra disponible");
                    break;
            }
        }
    }

    private static void playGame() {
        System.out.print("Digite la cantidad de filas del tablero: ");
        int rows = sc.nextInt();
        board.setRows(rows);
        System.out.print("Digite la cantidad de columnas del tablero: ");
        int columns = sc.nextInt();
        board.setColumns(columns);
        System.out.print("Digite el número de serpientes: ");
        int snakes = sc.nextInt();
        board.setSnakes(snakes);
        System.out.print("Digite el número de escaleras: ");
        int ladders = sc.nextInt();
        board.setLadders(ladders);
        System.out.println("************************************************");
        int size = columns * rows; // Dimensión del tablero
        for (int i = 0; i < size; i++) { // Añadir un nodo al tablero
            board.addNode(i , size);
        }
        board.addPlayers(); // Agrega los 3 jugadores
        board.showBoard();
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

