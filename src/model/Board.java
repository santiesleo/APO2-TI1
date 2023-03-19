package model;

import java.util.Random;

public class Board {

    public static final Random random = new Random();

    // Properties of the game board
    private int rows;
    private int columns;
    private int measures;
    private int snakes;
    private int ladders;

    // Players of the game
    private Player player1;
    private Player player2;
    private Player player3;

    // Links
    private Square head;
    private Square tail;

    // Helper attributes for game
    private int turn = 1;
    private int columnsT = 1;
    private int rowsT = 1;
    private int snakesID = 65;
    private int laddersID = 1;

    // Instance of the ScoreRegistry class
    private ScoreRegistry scoreRegistry;

    // Constructor
    public Board() {
        this.scoreRegistry = new ScoreRegistry();
    }

    // Getters and setters
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMeasures() {
        return measures;
    }

    public void setMeasures(int measures) {
        this.measures = measures;
    }

    public int getSnakes() {
        return snakes;
    }

    public void setSnakes(int snakes) {
        this.snakes = snakes;
    }

    public int getLadders() {
        return ladders;
    }

    public void setLadders(int ladders) {
        this.ladders = ladders;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public Square getHead() {
        return head;
    }

    public void setHead(Square head) {
        this.head = head;
    }

    public Square getTail() {
        return tail;
    }

    public void setTail(Square tail) {
        this.tail = tail;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getColumnsT() {
        return columnsT;
    }

    public void setColumnsT(int columnsT) {
        this.columnsT = columnsT;
    }

    public int getRowsT() {
        return rowsT;
    }

    public void setRowsT(int rowsT) {
        this.rowsT = rowsT;
    }

    public int getSnakesID() {
        return snakesID;
    }

    public void setSnakesID(int snakesID) {
        this.snakesID += snakesID;
    }

    public int getLaddersID() {
        return laddersID;
    }

    public void setLaddersID(int laddersID) {
        this.laddersID += laddersID;
    }

    public ScoreRegistry getScoreRegistry() {
        return scoreRegistry;
    }

    public void setScoreRegistry(ScoreRegistry scoreRegistry) {
        this.scoreRegistry = scoreRegistry;
    }

    //---------------ADD METHODS-----------------//
    /**
     * Add a Square object to the Board
     */
    public void addSquare(int i, int size) {
        // Creates a new Square object with an index of i+1
        Square square = new Square(i + 1);
        // If the list is empty, sets head and tail to square
        if (head == null) {
            head = square;
            tail = square;
        }
        else {
            // Otherwise, adds square to the end of the list
            tail.setNext(square);
            square.setPrevious(tail);
            tail = square;
            // If the index of the current tail is equal to the size, add the row and column in which each Square is located.
            if (tail.getNum() == size) {
                addRowAndColumnToSquare(head, getColumns());
            }
        }
    }

    /**
     * Add the row and column to each square
     */
    private void addRowAndColumnToSquare(Square current, int columns) {
        // If the current square is null, stops the recursion
        if (current == null) {
            return;
        }
        // If the previous square's column equals the total number of columns, adds a new row
        if (current.getPrevious() != null) {
            if (current.getPrevious().getColumn() == columns) {
                columnsT = 1;
                rowsT++;
            }
        }
        // Sets the current square's column and row
        current.setColumn(columnsT++);
        current.setRow(rowsT);
        // Recursively calls the method on the next square
        addRowAndColumnToSquare(current.getNext(), columns);
    }

    /**
     * Add players to the game
     */
    public void addPlayers() {
        addPlayers(head);
    }

    private void addPlayers(Square current) {
        // Checks if "current" is null, and if it is, stops the recursion
        if (current == null) {
            return;
        }
        // Checks if the "Player1", "Player2", and "Player3" fields of the "head" are null
        if (head.getPlayer1() == null && head.getPlayer2() == null && head.getPlayer3() == null) {
            // If they are, it creates three new "Player" objects with symbols "#", "%", and "$" and a score of 0
            Player player1 = new Player("#", 0);
            Player player2 = new Player("%", 0);
            Player player3 = new Player("$", 0);
            // Sets the "Player1", "Player2", and "Player3"
            setPlayer1(player1);
            setPlayer2(player2);
            setPlayer3(player3);
            // Sets in the "head" to the newly created "Player" objects
            head.setPlayer1(player1);
            head.setPlayer2(player2);
            head.setPlayer3(player3);
        } else {
            // If the "Player1", "Player2", and "Player3" fields of the "head" are not null, it simply sets the "Player 1", "Player2", and "Player3" fields of the "current" object to new "Player" objects with empty symbols and a score of 0.
            current.setPlayer1(new Player("", 0));
            current.setPlayer2(new Player("", 0));
            current.setPlayer3(new Player("", 0));
        }
        // Recursively calls the method on the next square
        addPlayers(current.getNext());
    }

    //---------------GENERATE METHODS-----------------//
    /**
     * Generate snakes in the game board
     */
    public void generateSnakes() {
        // Generates two random numbers to search for within the Squares to create the snakes
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        // Check if start and end numbers are not the same, then search for Squares to create snakes
        if (end != start) {
            searchNodes(start, end, head, head, "SNAKES");
        } else {
            // If the start and end numbers are the same, then restart the process
            generateSnakes();
        }
    }

    /**
     * Generate ladders in the game board
     */
    public void generateLadders() {
        // Generates two random numbers to search for within the Squares to create the ladders
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        // Check if start and end numbers are not the same, then search for Squares to create ladders
        if (end != start) {
            searchNodes(start, end, head, head, "LADDERS");
        } else {
            // If the start and end numbers are the same, then restart the process
            generateLadders();
        }
    }

    /**
     * Searches for Squares by comparing with the random numbers generated in the previous functions
     */
    private void searchNodes(int start, int end, Square current1, Square current2, String type) {
        if (current1.getNum() == start && current2.getNum() == end) {
            // If the Squares are found, confirms the status and proceeds to compare the rows
            confirmStatus(current1, current2, type);
        } else if (current1.getNum() == start && current2.getNum() != end) {
            // If the start Square is found but not the end Square, move to the next Square in the second Square
            searchNodes(start, end, current1, current2.getNext(), type);
        } else if (current1.getNum() != start && current2.getNum() == end) {
            // If the end Square is found but not the start Square, move to the next Square in the first Square
            searchNodes(start, end, current1.getNext(), current2, type);
        } else {
            // If neither Square is found, move to the next Squares in both Squares
            searchNodes(start, end, current1.getNext(), current2.getNext(), type);
        }
    }

    /**
     * Ensure that there are no ladders or snakes in the Square
     */
    private void confirmStatus(Square current1, Square current2, String type) {
        // Confirms that the Square where the snake or ladder will be placed is currently empty
        if (current1.getStatus().equals("") && current2.getStatus().equals("")) {
            // If the Squares are empty, compare the rows
            compareRows(current1, current2, type);
        } else {
            // If the Square is not empty, generate a new set of snakes or ladders
            if (type.equals("SNAKES")) {
                generateSnakes();
            } else if (type.equals("LADDERS")) {
                generateLadders();
            }
        }
    }

    /**
     * Compares that the nodes are positioned in different rows
     */
    private void compareRows(Square current1, Square current2, String type) {
        // If "type" is "SNAKES", then the rows are compared
        if (type.equals("SNAKES")) {
            // If the current row is equal to the row of the other current, then generate a new set of snakes or ladders
            if (current1.getRow() == current2.getRow()) {
                generateSnakes();
            }
            // If the current row is different to the row of the other current, then pass to next method
            else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() > current2.getRow()) {
                    generateSnakes(current1, current2);
                } else {
                    generateSnakes(current2, current1);
                }
            }
        }
        // If "type" is "LADDERS", then the rows are compared
        else if (type.equals("LADDERS")) {
            // If the current row is equal to the row of the other current, then generate a new set of snakes or ladders
            if (current1.getRow() == current2.getRow()) {
                generateLadders();
            }
            // If the current row is different to the row of the other current, then pass to next method
            else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() < current2.getRow()) {
                    generateLadders(current1, current2);
                } else {
                    generateLadders(current2, current1);
                }
            }
        }
    }

    /**
     * Generates the connection between Squares to become a snake
     */
    private void generateSnakes(Square current1, Square current2) {
        current1.setStatus(String.valueOf((char) snakesID));
        current2.setStatus(String.valueOf((char) snakesID));
        current1.setSnake_Ladder(current2);
    }

    /**
     * Generates the connection between Squares to become a ladder
     */
    private void generateLadders(Square current1, Square current2) {
        current1.setStatus(String.valueOf(laddersID));
        current2.setStatus(String.valueOf(laddersID));
        current1.setSnake_Ladder(current2);
    }

    //---------------DISPLAY METHODS-----------------//
    /**
     * Displays the game board
     */
    public void showBoard() {
        showBoard(tail, getRows(), getColumns());
    }

    private void showBoard(Square current, int rowCount, int columnCount) {
        // Checks if the current Square is not null and there are still rows to print
        if (current != null && rowCount > 0) {
            // If the Square number is divisible by 2 times the column count, it means that it is the end of the row, so a new line is printed
            if (current.getNum() % (columnCount * 2) == 0) {
                System.out.println();
                // Calls "showBoardRow" to print the row of Squares starting from the current Square and moves to the next row
                current = showBoardRow(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            } else {
                // Calls "showBoardRowInvested" to print the row of Squares starting from the current Square and moves to the next row
                current = showBoardRowInvested(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            }
        }
    }

    /**
     * Prints a row of Squares in ascending order
     * (ej: 1, 2, 3, 4, 5)
     */
    private Square showBoardRow(Square current, int columnCount) {
        Square lastNode = null;
        // Checks if the current Square is not null and there are still columns to print
        if (current != null && columnCount > 0) {
            // Calls "showPlayers" to get a string with the players on the current Square
            String players = showPlayers(current);
            // Formats a string with the Square number and the players on it
            String message = String.format("%7s", "[" + current.getNum() + players + "] ");
            // Prints the formatted string
            System.out.print(message);
            // Calls itself recursively to print the next Square in the row
            lastNode = showBoardRow(current.getPrevious(), columnCount - 1);
        }
        // If there are no more Squares to print in the row, returns the last Square printed
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    /**
     * Prints a row of Squares in descending order
     * (ej: 5, 4, 3, 2, 1)
     */
    private Square showBoardRowInvested(Square current, int columnCount) { // Imprime de mayor a menor (ej: 5, 4, 3, 2, 1)
        Square lastNode = null;
        // Checks if the current Square is not null and there are still columns to print
        if (current != null && columnCount > 0) {
            // Calls itself recursively to print the next Square in the row
            lastNode = showBoardRowInvested(current.getPrevious(), columnCount - 1);
            // Calls "showPlayers" to get a string with the players on the current Square
            String players = showPlayers(current);
            // Formats a string with the Square number and the players on it
            String message = String.format("%7s", "[" + current.getNum() + players + "] ");
            // Prints the formatted string
            System.out.print(message);
        } else {
            // If there are no more Squares to print in the row, prints a new line if there are no more Squares to print in the row
            System.out.println();
        }
        // If there are no more Squares to print in the row, returns the last Square printed
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    /**
     * Returns a String representing the names of the players associated with a specific "Square" object
     */
    private String showPlayers(Square current) {
        // Create an empty String variable named "players"
        String players = "";
        // Check if the "current" Square object has a non-null Player1
        if (current.getPlayer1() != null) {
            // If the Player1 is not null, add the name of the Player1 to the "players" String variable
            players = players + current.getPlayer1().getName();
        }
        // Check if the "current" Square object has a non-null Player2
        if (current.getPlayer2() != null) {
            // If the Player2 is not null, add the name of the Player2 to the "players" String variable
            players = players + current.getPlayer2().getName();
        }
        // Check if the "current" Square object has a non-null Player3
        if (current.getPlayer3() != null) {
            // If the Player3 is not null, add the name of the Player3 to the "players" String variable
            players = players + current.getPlayer3().getName();
        }
        return players;
    }

    /**
     * Displays the game board with snakes and ladders
     */
    public void showSnakesAndLadders() {
        showSnakesAndLadders(tail, getRows(), getColumns());
    }

    private void showSnakesAndLadders(Square current, int rowCount, int columnCount) {
        // Performs the same process as the method "showBoard"
        if (current != null && rowCount > 0) {
            if (current.getNum() % (columnCount * 2) == 0) {
                System.out.println();
                current = showSnakesAndLaddersBoardRow(current, columnCount);
                showSnakesAndLadders(current, rowCount - 1, columnCount);
            } else {
                current = showSnakesAndLaddersRowInvested(current, columnCount);
                showSnakesAndLadders(current, rowCount - 1, columnCount);
            }
        }
    }

    private Square showSnakesAndLaddersBoardRow(Square current, int columnCount) {
        // Performs the same process as the method "showBoardRow"
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            String message = String.format("%7s", "[" + current.getStatus() + "] ");
            System.out.print(message);
            lastNode = showSnakesAndLaddersBoardRow(current.getPrevious(), columnCount - 1);
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private Square showSnakesAndLaddersRowInvested(Square current, int columnCount) {
        // Performs the same process as the method "showBoardRowInvested"
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            lastNode = showSnakesAndLaddersRowInvested(current.getPrevious(), columnCount - 1);
            String message = String.format("%7s", "[" + current.getStatus() + "] ");
            System.out.print(message);

        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    //---------------MOVE METHODS-----------------//
    /**
     * Manages the turn and returns an integer value representing the current turn
     */
    public int manageTurn() {
        int turn;
        if (getTurn() == 1) {
            // If the current turn is 1, assign the 1 to "turn"
            turn = 1;
        } else if (getTurn() == 2) {
            // If the current turn is 2, assign the 2 to "turn"
            turn = 2;
        } else {
            // If the current turn is neither 1 nor 2, assign the  3 to "turn"
            turn = 3;
        }
        // Return the value of "turn"
        return turn;
    }

    /**
     * Manages the turn and returns a Player object representing the current player
     */
    public Player manageTurnPlayer() {
        Player player;
        if (getTurn() == 1) {
            // If the current turn is 1, assign "player1" to "player"
            player = player1;
        } else if (getTurn() == 2) {
            // If the current turn is 2, assign "player2" to "player"
            player = player2;
        } else {
            // If the current turn is neither 1 nor 2, assign "player3" to "player"
            player = player3;
        }
        // Return the Player object "player"
        return player;
    }

    /**
     * Simulate the rolling of a dices, it returns a random integer between 1 and 6
     */
    public int throwDice() {
        return random.nextInt(6) + 1;
    }

    /**
     * Moves the player to new Square
     */
    public boolean movePlayer(Square current, Square squareToMove, Player playerToMove) {
        // If the Square to move to is the tail Square, then move the player to the tail Square and remove the player from the current Square
        if (squareToMove == tail) {
            if (current.getPlayer1() == playerToMove) {
                tail.setPlayer1(playerToMove);
                current.setPlayer1(null);
            } else if (current.getPlayer2() == playerToMove) {
                tail.setPlayer2(playerToMove);
                current.setPlayer2(null);
            } else if (current.getPlayer3() == playerToMove) {
                tail.setPlayer3(playerToMove);
                current.setPlayer3(null);
            }
            return true;
        }
        // Otherwise, if the Square to move to is not the tail Square, then move the player to that Square, and remove the player from the current Square
        else {
            if (current.getPlayer1() == playerToMove) {
                if (squareToMove.getSnake_Ladder() != null) {
                    // If the Square to move to have a snake or a ladder, then move the player to the end of the snake/ladder
                    squareToMove.getSnake_Ladder().setPlayer1(playerToMove);
                }
                else {
                    // Otherwise, move the player to the square. Repeat the same for the other cases
                    squareToMove.setPlayer1(playerToMove);
                }
                current.setPlayer1(null);
            } else if (current.getPlayer2() == playerToMove) {
                if (squareToMove.getSnake_Ladder() != null) {
                    squareToMove.getSnake_Ladder().setPlayer2(playerToMove);
                } else {
                    squareToMove.setPlayer2(playerToMove);
                }
                current.setPlayer2(null);
            } else if (current.getPlayer3() == playerToMove) {
                if (squareToMove.getSnake_Ladder() != null) {
                    squareToMove.getSnake_Ladder().setPlayer3(playerToMove);
                } else {
                    squareToMove.setPlayer3(playerToMove);
                }
                current.setPlayer3(null);
            }
            return false;
        }
    }

    /**
     * Searches for a player's Square in a linked list of Squares
     */
    public Square searchPlayerSquare(Player goal) {
        return searchPlayerSquare(head, goal);
    }

    private Square searchPlayerSquare(Square current, Player goal) {
        // Checks if the current Square is null, and if it is, return null
        if (current == null) {
            return null;
        }
        if (current.getPlayer1() == goal) {
            // If the first player in the current Square is the goal player, return the current Square
            return current;
        } else if (current.getPlayer2() == goal) {
            // If the second player in the current Square is the goal player, return the current Square
            return current;
        } else if (current.getPlayer3() == goal) {
            // If the third player in the current Square is the goal player, return the current Square
            return current;
        }
        // Recursively call the method with the next Square in the linked list
        return searchPlayerSquare(current.getNext(), goal);
    }

    /**
     * Takes a current Square and a number of squares to advance
     */
    public Square calculateSquares2Move(Square current, int squares2Advance) {
        if (current == tail) {
            // If the current Square is the tail, we return the tail
            return tail;
        } else if (squares2Advance == 0) {
            // If there are no Squares to advance, we return the current Square
            return current;
        }
        // If neither of the above conditions are met, we recursively call the method with the next Square and a decreased number of Squares to advance
        return calculateSquares2Move(current.getNext(), squares2Advance - 1);
    }

    /**
     * Passes the turn to the next player
     */
    public void passTurn(){
        if (getTurn() == 1) {
            // If the current turn is 1, set the turn to 2
            setTurn(2);
        } else if (getTurn() == 2) {
            // If the current turn is 2, set the turn to 3
            setTurn(3);
        } else {
            // If the current turn is neither 1 nor 2, set the turn to 1
            setTurn(1);
        }
    }

    //---------------SCORE REGISTRY METHODS-----------------//

    /**
     * Adds the score of player to the score registry
     */
    public void addPlayer2ScoreRegistry(Square square, double score) {
        if (square.getPlayer1().getName().equals("#")) {
            // If the player in the tail is player 1, the score is added to the score registry
            Player player2Update = new Player("#", score);
            scoreRegistry.add(player2Update);
        } else if (square.getPlayer2().getName().equals("%")) {
            // If the player in the tail is player 2, the score is added to the score registry
            Player player2Update = new Player("%", score);
            scoreRegistry.add(player2Update);
        } else if (square.getPlayer3().getName().equals("$")) {
            // If the player in the tail is player 3, the score is added to the score registry
            Player player2Update = new Player("$", score);
            scoreRegistry.add(player2Update);
        } else {
            // If none of the conditions above are met, do nothing
        }
    }

    /**
     * Prints the ranking of players in the score registry in reverse order
     */
    public void scoreRanking(){
        System.out.println("\t\t\tRANKING");
        scoreRegistry.reverseInOrder();
        System.out.print("------------------------------------------------\n");
    }

    //---------------RESET METHODS-----------------//

    /**
     * Resets the board to its initial state
     */
    public void resetAll() {
        resetAll(getHead());
    }

    private void resetAll(Square current) {
        // If the current Square is null, then the end of the linked list has been reached, so return
        if (current == null) {
            return;
        }
        // If the current Square is the only Square in the list, set both head and tail to null
        if (current.getNext() == null && current.getPrevious() == null) {
            setHead(null);
            setTail(null);
            return;
        }
        // If the current Square is the head of the list, then set current to null and set the next Square as the new head and call resetAll() starting from the new head
        if (current.getPrevious() == null) {
            current.getNext().setPrevious(null);
            setHead(current.getNext());
            resetAll(getHead());
        }
    }

}