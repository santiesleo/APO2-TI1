package model;

public class Square {

    //Datos
    private int num;
    private int column;
    private int row;
    private Player player1;
    private Player player2;
    private Player player3;

    public Square(int num) {
        this.num = num;
        this.column = 0;
        this.row = 0;
        this.player1 = null;
        this.player2 = null;
        this.player3 = null;
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

    //Enlaces
    private Square next;
    private Square previous;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

}