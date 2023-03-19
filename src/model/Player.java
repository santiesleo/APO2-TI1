package model;

public class Player {

    //Datos
    String name;
    double score;

    //Enlaces
    private Player right;
    private Player left;

    //Constructor
    public Player(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
