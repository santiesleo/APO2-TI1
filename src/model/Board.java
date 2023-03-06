package model;

public class Board {
    private ScoreRegistry scoreRegistry;
    private Square head;
    private Square tail;
    private int columns;
    private int rows;
    private int snakes;
    private int ladders;
    private int columnsT = 1;
    private int rowsT = 1;

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
            head.setPlayer1(new Player("*"));
            head.setPlayer2(new Player("!"));
            head.setPlayer3(new Player("#"));
        } else {
            current.setPlayer1(new Player(""));
            current.setPlayer2(new Player(""));
            current.setPlayer3(new Player(""));
        }
        addPlayers(current.getNext());
    }

    public void addNode(int i, int size) {
        Square node = new Square(i + 1);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
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
            System.out.print("[" + current.getNum() + players + "] ");
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
            System.out.print("[" + current.getNum() + players + "] ");
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