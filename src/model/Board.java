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

    public int manageTurn() {
        int turn;
        if (getTurn() == 1) {
            turn = 1;
        } else if (getTurn() == 2) {
            turn = 2;
        } else {
            turn = 3;
        }
        return turn;
    }

    public Player manageTurnPlayer() {
        Player player;
        if (getTurn() == 1) {
            player = player1;
        } else if (getTurn() == 2) {
            player = player2;
        } else {
            player = player3;
        }
        return player;
    }

    public void passTurn(){
        if (getTurn() == 1) {
            setTurn(2);
        } else if (getTurn() == 2) {
            setTurn(3);
        } else {
            setTurn(1);
        }
    }

    public int throwDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public Square searchPlayerSquare(Player goal) {
        return searchPlayerSquare(head, goal);
    }

    private Square searchPlayerSquare(Square current, Player goal) {
        if (current == null) {
            return null;
        }
        if (current.getPlayer1() == goal) {
            return current;
        } else if (current.getPlayer2() == goal) {
            return current;
        } else if (current.getPlayer3() == goal) {
            return current;
        }
        return searchPlayerSquare(current.getNext(), goal);
    }

    public Square calculateSquares2Move(Square current, int squares2Advance) {
        if (current == tail) {
            return tail;
        } else if (squares2Advance == 0) {
            return current;
        }
        return calculateSquares2Move(current.getNext(), squares2Advance - 1);
    }

    public boolean movePlayer(Square current, Square squareToMove, Player playerToMove) {
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
        } else {
            if (current.getPlayer1() == playerToMove) {
                if (squareToMove.getSnake_Ladder() != null) {
                    squareToMove.getSnake_Ladder().setPlayer1(playerToMove);
                } else {
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

    //NO ESTÁ TERMINADO!!!
    public void addPlayer2ScoreRegistry(Square square, double score) {
        if (square.getPlayer1().getName().equals("#")) {
            Player player2Update = new Player("#", score);
            scoreRegistry.add(player2Update);
        } else if (square.getPlayer2().getName().equals("%")) {
            Player player2Update = new Player("%", score);
            scoreRegistry.add(player2Update);
        } else if (square.getPlayer3().getName().equals("$")) {
            Player player2Update = new Player("$", score);
            scoreRegistry.add(player2Update);
        } else {

        }
    }





    public void showBoard() {
        showBoard(tail, getRows(), getColumns());
    }

    private void showBoard(Square current, int rowCount, int columnCount) {
        if (current != null && rowCount > 0) {
            if (current.getNum() % (columnCount * 2) == 0) {
                System.out.println();
                current = showBoardRow(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            } else {
                current = showBoardRowInvested(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            }
        }
    }

    private Square showBoardRow(Square current, int columnCount) { // Imprime de menor a mayor (ej: 1, 2, 3, 4, 5)
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            String players = showPlayers(current);
            String message = String.format("%7s", "[" + current.getNum() + players + "] ");
            System.out.print(message);
            lastNode = showBoardRow(current.getPrevious(), columnCount - 1);
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private Square showBoardRowInvested(Square current, int columnCount) { // Imprime de mayor a menor (ej: 5, 4, 3, 2, 1)
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            lastNode = showBoardRowInvested(current.getPrevious(), columnCount - 1);
            String players = showPlayers(current);
            String message = String.format("%7s", "[" + current.getNum() + players + "] ");
            System.out.print(message);
        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private String showPlayers(Square current) {
        String players = "";
        if (current.getPlayer1() != null) {
            players = players + current.getPlayer1().getName();
        }
        if (current.getPlayer2() != null) {
            players = players + current.getPlayer2().getName();
        }
        if (current.getPlayer3() != null) {
            players = players + current.getPlayer3().getName();
        }
        return players;
    }



    public void showSnakesAndLadders() {
        showSnakesAndLadders(tail, getRows(), getColumns());
    }

    private void showSnakesAndLadders(Square current, int rowCount, int columnCount) {
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

    public void resetAll() {
        resetAll(getHead());
    }

    private void resetAll(Square current) {
        if (current == null) {
            return;
        }
        if (current.getNext() == null && current.getPrevious() == null) {
            setHead(null);
            setTail(null);
            return;
        }
        if (current.getPrevious() == null) {
            current.getNext().setPrevious(null);
            setHead(current.getNext());
            resetAll(getHead());
        }
    }

    public void scoreRanking(){
        System.out.println("\t\t\tRANKING");
        scoreRegistry.reverseInOrder();
        System.out.print("------------------------------------------------\n");
    }

}