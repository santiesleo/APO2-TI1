package model;

public class Player {
    //Datos
    char name;
    String time;

    //Enlaces
    private Player right;
    private Player left;

    //Constructor
    public Player(char name) {
        this.name = name;
        this.time = "";
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Player getRight() {
        return right;
    }

    public void setRight(Player right) {
        this.right = right;
    }

    public Player getLeft() {
        return left;
    }

    public void setLeft(Player left) {
        this.left = left;
    }
}
