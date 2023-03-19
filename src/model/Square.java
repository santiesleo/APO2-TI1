package model;

public class Square {

    // Data
    private int num;
    private int row;
    private int column;
    private String status;
    private Player player1;
    private Player player2;
    private Player player3;

    // Links
    private Square next;
    private Square previous;
    private Square snake_Ladder;

    // Constructor
    public Square(int num) {
        this.num = num;
        this.row = 0;
        this.column = 0;
        this.player1 = null;
        this.player2 = null;
        this.player3 = null;
        this.status = "";
    }

    // Getters and setters
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Square getNext() {
        return next;
    }

    public void setNext(Square next) {
        this.next = next;
    }

    public Square getPrevious() {
        return previous;
    }

    public void setPrevious(Square previous) {
        this.previous = previous;
    }

    public Square getSnake_Ladder() {
        return snake_Ladder;
    }

    public void setSnake_Ladder(Square snake_Ladder) {
        this.snake_Ladder = snake_Ladder;
    }

}