package model;

import java.util.Random;

public class Board {

    public static final Random random = new Random();
    private ScoreRegistry scoreRegistry;
    private Square head;
    private Square tail;
    private Player player1;
    private Player player2;
    private Player player3;
    int turn = 1;
    private int columns;
    private int rows;
    private int measures;
    private int snakes;
    private int ladders;
    private int columnsT = 1;
    private int rowsT = 1;
    private int s = 65;
    private int l = 1;

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

    public ScoreRegistry getScoreRegistry() {
        return scoreRegistry;
    }

    public void setScoreRegistry(ScoreRegistry scoreRegistry) {
        this.scoreRegistry = scoreRegistry;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s += s;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l += l;
    }

    public int manageTurn(){
        int turn;
        if(getTurn()== 1){
            turn = 1;
        }else if(getTurn() == 2){
            turn = 2;
        }else {
            turn = 3;
        }
        return turn;
    }

    public Player manageTurnPlayer(int turn){
        Player player;
        if(getTurn()== 1){
            player = player1;
            setTurn(2);
        }else if(getTurn() == 2){
            player = player2;
            setTurn(3);
        }else {
            player = player3;
            setTurn(1);
        }
        return player;
    }

    public int throwDice(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public Square searchPlayerSquare(Player goal){
        return searchPlayerSquare(head, goal);
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
    public void addPlayer2ScoreRegistry(Square square) {
        if (square.getPlayer1() != null) {
            //square.getPlayer1().setTime();
            scoreRegistry.add(square.getPlayer1());
        } else if (square.getPlayer2() != null) {
            scoreRegistry.add(square.getPlayer2());
        } else if (square.getPlayer3() != null) {
            scoreRegistry.add(square.getPlayer3());
        } else {

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
            Player player1 = new Player("#");
            Player player2 = new Player("%");
            Player player3 = new Player("$");
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

    private void searchNodes(int start, int end, Square current1, Square current2, String type) {// Busca los nodos comparando con los números aleatorios
        if (current1.getNum() == start && current2.getNum() == end) {
            confirmStatus(current1, current2, type);

        } else if (current1.getNum() == start && current2.getNum() != end) {
            searchNodes(start, end, current1, current2.getNext(), type);

        } else if (current1.getNum() != start && current2.getNum() == end) {
            searchNodes(start, end, current1.getNext(), current2, type);

        } else {
            searchNodes(start, end, current1.getNext(), current2.getNext(), type);
        }
    }

    private void confirmStatus(Square current1, Square current2, String type) {// Asegura que en la casilla no halla ninguna escalera ni serpiente
        if (current1.getStatus().equals("") && current2.getStatus().equals("")) {
            compareRows(current1, current2, type);
        } else {
            if (type.equals("SNAKES")){
                generateSnakes();
            } else if (type.equals("LADDERS")) {
                generateLadders();
            }
        }
    }

    private void compareRows(Square current1, Square current2, String type) { // Compara que los nodos estén posicionados en filas distintas
        if (type.equals("SNAKES")){
            if (current1.getRow() == current2.getRow()) {
                generateSnakes();
            } else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() > current2.getRow()) {
                    generateSnakes(current1, current2);
                } else {
                    generateSnakes(current2, current1);
                }
            }
        } else if (type.equals("LADDERS")) {
            if (current1.getRow() == current2.getRow()) {
                generateLadders();
            } else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() < current2.getRow()) {
                    generateLadders(current1, current2);
                } else {
                    generateLadders(current2, current1);
                }
            }
        }
    }

    public void generateSnakes() { // Genera los dos números aleatorios a buscar en los nodos para las serpientes
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        if (end != start) {
            searchNodes(start, end, head, head, "SNAKES");
        } else {
            generateSnakes();
        }
    }

    public void generateLadders() { // Genera los dos números aleatorios a buscar en los nodos para las serpientes
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        if (end != start) {
            searchNodes(start, end, head, head, "LADDERS");
        } else {
            generateLadders();
        }
    }

    private void generateSnakes(Square current1, Square current2) {//Genera la union entre casillas para convertirse en serpiente
        current1.setStatus(String.valueOf((char) s));
        current2.setStatus(String.valueOf((char) s));
        current1.setSnake_Ladder(current2);
        System.out.println("cabeza de la serpiente " + current1.getNum());
        System.out.println("cola de la serpiente " + current1.getSnake_Ladder().getNum());
    }

    private void generateLadders(Square current1, Square current2) {//Genera la union entre casillas para convertirse en escalera
        current1.setStatus(String.valueOf(l));
        current2.setStatus(String.valueOf(l));
        current1.setSnake_Ladder(current2);
        System.out.println("inicio de la escalera " + current1.getNum());
        System.out.println("fin de la escalera " + current1.getSnake_Ladder().getNum());
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
            System.out.print("[" + current.getStatus() + "] ");
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
            System.out.print("[" + current.getStatus() + "] ");
        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

}