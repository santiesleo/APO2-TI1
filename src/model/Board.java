package model;

import java.util.Random;

public class Board {
    private ScoreRegistry scoreRegistry;
    private Square head;
    private Square tail;
    private Player player1;
    private Player player2;
    private Player player3;
    int turn = 1;
    private int columns;
    private int rows;
    private int snakes;
    private int ladders;
    private int columnsT = 1;
    private int rowsT = 1;

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

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
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

    public ScoreRegistry getScoreRegistry() {
        return scoreRegistry;
    }

    public void setScoreRegistry(ScoreRegistry scoreRegistry) {
        this.scoreRegistry = scoreRegistry;
    }

    public String manageTurn(int turn){
        String message = "";
        if(getTurn()== 1){
            message = "Jugador " + player1.getName() + " it's your turn";
            setTurn(2);
        }else if(getTurn() == 2){
            message = "Jugador " + player2.getName() + " it's your turn";
            setTurn(3);
        }else {
            message = "Jugador " + player3.getName() + " it's your turn";
            setTurn(1);
        }
        return message;
    }

    public int throwDice(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public void searchPlayerSquare(Player goal){
        searchPlayerSquare(head, goal);
    }

    private Square searchPlayerSquare(Square current, Player goal){
        if(current == null){
            return null;
        }if(current.getPlayer1() == goal){
            return current;
        } else if (current.getPlayer2() == goal) {
            return current;
        } else if (current.getPlayer3() == goal) {
            return current;
        }
        return searchPlayerSquare(current.getNext(), goal);
    }

    public Square calculateSquares2Move(Square current, int squares2Advance){
        if(current == tail) {
            return tail;
        } else if (squares2Advance == 0) {
            return current;
        }
        return calculateSquares2Move(current.getNext(), squares2Advance - 1);
    }

    public boolean movePlayer(Square current, Square squareToMove, Player playerToMove){
        if(squareToMove == tail){
            if(current.getPlayer1() == playerToMove){
                tail.setPlayer1(playerToMove);
                current.setPlayer1(null);
            }else if(current.getPlayer2() == playerToMove){
                tail.setPlayer2(playerToMove);
                current.setPlayer2(null);
            }else if(current.getPlayer3() == playerToMove){
                tail.setPlayer3(playerToMove);
                current.setPlayer3(null);
            }
            return true;
        } else {
            if(current.getPlayer1() == playerToMove){
                squareToMove.setPlayer1(playerToMove);
                current.setPlayer1(null);
            }else if(current.getPlayer2() == playerToMove){
                squareToMove.setPlayer2(playerToMove);
                current.setPlayer2(null);
            }else if(current.getPlayer3() == playerToMove){
                squareToMove.setPlayer3(playerToMove);
                current.setPlayer3(null);
            }
            return false;
        }
    }

    //NO ESTÁ TERMINADO!!!
    public void addPlayer2ScoreRegistry(Square square){
        if (square.getPlayer1() != null){
            //square.getPlayer1().setTime();
            scoreRegistry.add(square.getPlayer1());
        } else if (square.getPlayer2() != null) {
            scoreRegistry.add(square.getPlayer2());
        }else if (square.getPlayer3() != null) {
            scoreRegistry.add(square.getPlayer3());
        }else {

        }
    }

    public void addPlayers() {
        addPlayers(head);
    }
    private void addPlayers(Square current) {
        if (current == null) {
            return;
        }
        if (head.getPlayer1() == null && head.getPlayer2() == null && head.getPlayer3() == null) {
            Player player1 = new Player("*");
            Player player2 = new Player("!");
            Player player3 = new Player("2");
            setPlayer1(player1);
            setPlayer2(player2);
            setPlayer3(player3);
            head.setPlayer1(player1);
            head.setPlayer2(player2);
            head.setPlayer3(player3);
        } else {
            current.setPlayer1(new Player(""));
            current.setPlayer2(new Player(""));
            current.setPlayer3(new Player(""));
        }
        addPlayers(current.getNext());
    }

    public void addSquare(int i, int size) {
        Square square = new Square(i + 1);
        if (head == null) {
            head = square;
            tail = square;
        } else {
            tail.setNext(square);
            square.setPrevious(tail);
            tail = square;
            if (tail.getNum() == size) {
                addColumAndRow(head, getColumns());
            }
        }
    }

    private void addColumAndRow(Square current, int columns) { // Añade a cada Square su respectiva columna y fila
        if (current == null) {
            return;
        }
        if (current.getPrevious() != null) {
            if (current.getPrevious().getColumn() == columns) {
                columnsT = 1;
                rowsT++;
            }
        }
        current.setColumn(columnsT++);
        current.setRow(rowsT);
        addColumAndRow(current.getNext(), columns);
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

    private String showPlayers(Square current){
        String players = "";
        if(current.getPlayer1() != null){
            players = players + current.getPlayer1().getName();
        }
        if(current.getPlayer2() != null){
            players = players + current.getPlayer2().getName();
        }
        if(current.getPlayer3() != null){
            players = players + current.getPlayer3().getName();
        }
        return players;
    }

    public void GenerateSnakes(){
        GenerateSnakes(1);
    }
    private void GenerateSnakes(int num){
        System.out.println("si");
    }
}