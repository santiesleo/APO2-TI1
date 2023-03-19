package ui;

import java.util.Scanner;

import model.Board;

public class Main {

    public static final Scanner sc = new Scanner(System.in);
    public static final Board board = new Board();

    public static void main(String[] args) {
        Main manager = new Main();
        manager.showMainMenu();
    }

    /**
     * The method showMainMenu, displays the main menu, allowing the user to choose between play and exit of the game.
     */
    public void showMainMenu() {
        // Display welcome message
        System.out.println("************************************************");
        System.out.println("\t\tWELCOME TO SNAKES AND LADDERS\t\t");

        // Initialize stop flag for menu loop
        boolean stopFlag = false;

        // Display main menu options until user chooses to exit
        while (!stopFlag) {
            System.out.println("************************************************");
            System.out.println("\t\tMAIN MENU:");
            System.out.println("\t\t[1] Play");
            System.out.println("\t\t[2] Exit");
            System.out.print("\t\tSelect an option: ");

            // Read user input from console
            int mainOption = sc.nextInt();
            System.out.println("************************************************");

            // Determine action based on user input
            switch (mainOption) {
                case 1:
                    startGame();
                    showGameMenu();
                    break;
                case 2:
                    System.out.println("\t\tEXIT SUCCESSFULLY");
                    System.out.println("************************************************");
                    stopFlag = true;
                    break;
                default:
                    // Handle invalid input
                    System.out.println("\t\tOPTION NOT AVAILABLE");
                    break;
            }
        }
    }

    /**
     * The method showGameMenu, displays the game menu, allowing the user to choose between throwing the dice and watching the ladders and snakes on the board.
     */
    public void showGameMenu() {
        // Start timer
        long startTime = System.currentTimeMillis();

        // Initialize stop flag for menu loop
        boolean stopFlag = false;

        // Menu loop
        while (!stopFlag) {
            // Display menu options and current game state
            System.out.println("************************************************");
            System.out.print("\t\tBOARD GAME");
            board.showBoard(); // Display the current state of the game board
            String message = manageTurn(board.manageTurn()); // Get the current turn message from the Board class
            System.out.println(message); // Display the message for the current turn
            System.out.println("[1] Throw dice");
            System.out.println("[2] Watch ladders and snakes");

            // Read user input from console
            System.out.print("Select an option: ");
            int mainOption = sc.nextInt();

            // Determine action based on user input
            switch (mainOption) {
                case 1:
                    // If user selects option 1, move the player
                    boolean flagPlayer = movePlayer();
                    if(flagPlayer){
                        // If a winner is found, stop the game loop and display scores
                        long endTime = System.currentTimeMillis();
                        double totalTime =  ((endTime-startTime)/1000.0);
                        addPlayer2ScoreRegistry(totalTime); // Add the current player to the score registry
                        System.out.println("We have a winner, congratulations!");
                        board.scoreRanking(); // Display the scores for all players
                        resetAll(); // Reset the game state to start a new game
                        stopFlag = true; // Stop the game loop
                    }
                    break;
                case 2:
                    // If user selects option 2, display the Snakes and Ladders board
                    System.out.println("------------------------------------------------");
                    System.out.print("\t\tBOARD SNAKES AND LADDERS");
                    board.showSnakesAndLadders(); // Display the Snakes and Ladders board
                    System.out.print("\n------------------------------------------------\n");
                    break;
                default:
                    // If user selects an option that is not available, display an error message
                    System.out.println("------------------------------------------------");
                    System.out.print("\t\tOPTION NOT AVAILABLE");
                    System.out.print("\n------------------------------------------------\n");
                    break;
            }
        }
    }

    /**
     * The method startGame, initialize the game, first request the board attributes and then generate the same
     */
    private void startGame() {
        // Set rows of the board
        System.out.print("Enter the number of rows on the board: ");
        int rows = sc.nextInt();
        if (rows <= 1 || rows >= 90) {
            System.out.println("------------------------------------------------");
            System.out.println("Invalid number of rows, try again.");
            System.out.println("------------------------------------------------");
            System.out.println("************************************************");
            startGame();
            return;
        } else {
            board.setRows(rows);
        }
        // Set columns of the board
        System.out.print("Enter the number of columns on the board: ");
        int columns = sc.nextInt();
        if (columns <= 1 || columns >= 90) {
            System.out.println("------------------------------------------------");
            System.out.println("Invalid number of columns, try again.");
            System.out.println("------------------------------------------------");
            System.out.println("************************************************");
            startGame();
            return;
        } else {
            board.setColumns(columns);
        }
        // Set number of snakes and ladders of the board
        System.out.print("Enter the number of snakes: ");
        int snakes = sc.nextInt();
        System.out.print("Enter the number of ladders: ");
        int ladders = sc.nextInt();
        if (snakes < 0 || ladders < 0) {
            System.out.println("------------------------------------------------");
            System.out.println("Invalid number of snakes or ladders, try again.");
            System.out.println("------------------------------------------------");
            System.out.println("************************************************");
            startGame();
            return;
        } else if (snakes + ladders >= ((board.getRows() * board.getColumns() - 2) / 2)) {
            System.out.println("------------------------------------------------");
            System.out.println("Many snakes or ladders, try again.");
            System.out.println("------------------------------------------------");
            System.out.println("************************************************");
            startGame();
            return;
        } else {
            board.setSnakes(snakes);
            board.setLadders(ladders);
        }

        // Board measurements
        int measures = columns * rows;
        board.setMeasures(measures);

        // Add a square to the board
        for (int i = 0; i < measures; i++) {
            board.addSquare(i, measures);
        }

        // Add three players to the game
        board.addPlayers();

        // Add the snakes to the board
        for (int i = 0; i < snakes; i++) {
            board.generateSnakes();
            board.setSnakesID(1);
        }

        // Add the ladders to the board
        for (int i = 0; i < ladders; i++) {
            board.generateLadders();
            board.setLaddersID(1);
        }
    }


    public void addPlayer2ScoreRegistry(double totalTime){
        double score = (600-totalTime)/6.0;
        board.addPlayer2ScoreRegistry(board.getTail(), score);
    }

    public boolean movePlayer() {
        int squaresToMove = board.throwDice();
        System.out.println("------------------------------------------------");
        System.out.print("\t\tMove " + squaresToMove + " squares.\n");
        System.out.print("------------------------------------------------\n");

        int turn = board.manageTurn();
        boolean flag = board.movePlayer(board.searchPlayerSquare(board.manageTurnPlayer()),
                board.calculateSquares2Move(board.searchPlayerSquare(board.manageTurnPlayer()),
                        squaresToMove), board.manageTurnPlayer());
        if (!flag) { //Se valida si el jugador llegó a la última casilla
            board.passTurn();
        }
        return flag;
    }

    /**
     * The method manageTurn, takes the current turn as input and returns a message indicating which player's turn it is.
     */
    public String manageTurn(int turn) {
        String message = "";
        if (turn == 1) {
            message = "\nPlayer #, it's your turn.";
        } else if (turn == 2) {
            message = "\nPlayer %, it's your turn.";
        } else {
            message = "\nPlayer $, it's your turn.";
        }
        return message;
    }

    /**
     * The method resetAll, resets the board by setting all players to null, changing the IDs of snakes and ladders, and resetting the board state.
     */
    public void resetAll(){
        board.setPlayer1(null);
        board.setPlayer2(null);
        board.setPlayer3(null);
        board.setSnakesID(-board.getSnakesID()+65);
        board.setLaddersID(-board.getLaddersID()+1);
        board.setColumnsT(1);
        board.setRowsT(1);
        board.resetAll();
    }

}

