package model;

import java.util.Random;

public class Board {
    private ScoreRegistry scoreRegistry;
    private Square head;
    private Square tail;
    private int columns;
    private int rows;
    private int measures;
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

    public int getMeasures() {return measures;}

    public void setMeasures(int measures) {this.measures = measures;}

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

    public void GenerateSnakes() {//Genera los dos numeros aleatorios a buscar en los nodos para las serpientes
        Random random = new Random();
        int num1 = random.nextInt(getMeasures() - 1) + 1;
        int num2 = random.nextInt(getMeasures() - 1) + 1;
        if (num2 != num1) {
            searchNodes(num1, num2, head, head);
        } else {
            GenerateSnakes();
        }
    }

    private void searchNodes(int num1, int num2, Square current1, Square current2) {//Busca los nodos comparando con los numeros aletorios
        if (current1.getNum() == num1 && current2.getNum() == num2) {
            confirmStatus(current1, current2);

        } else if (current1.getNum() == num1 && current2.getNum() != num2) {
            searchNodes(num1, num2, current1, current2.getNext());

        } else if (current1.getNum() != num1 && current2.getNum() == num2) {
            searchNodes(num1, num2, current1.getNext(), current2);

        } else {
            searchNodes(num1, num2, current1.getNext(), current2.getNext());
        }
    }

    private void confirmStatus(Square current1, Square current2) {// se asegura de que el nodo no sea previamente una columna o una serpiente
        if (current1.getStatus() != "" || current2.getStatus() != "") {
            GenerateSnakes();
        } else {
            compareRows(current1, current2);
        }
    }

    private void compareRows(Square current1, Square current2) {//compara que los nodos esten posicionados en filas distintas
        if (current1.getRow() == current2.getRow()) {
            GenerateSnakes();
        } else if (current1.getRow() != current2.getRow()) {
            if (current1.getRow() > current2.getRow()) {
                GenerateSnakes(current1, current2);
            } else {
                GenerateSnakes(current2, current1);
            }
        }
    }

    private void GenerateSnakes(Square current1, Square current2) {//Genera la union entre casillas para convertirse en serpiente
        current1.setSnake_Ladder(current2);
        System.out.println("cabeza de la serpiente " + current1.getNum());
        System.out.println("cola de la serpiente " + current1.getSnake_Ladder().getNum());
    }
}