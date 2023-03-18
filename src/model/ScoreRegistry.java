package model;

import java.text.DecimalFormat;

public class ScoreRegistry {

    public static final DecimalFormat df = new DecimalFormat("#.00000");
    private Player root;

    public void add(Player player) {
        if (root == null) {
            root = player;
        } else {
            add(root, player);
        }
    }

    private void add(Player current, Player player) {
        if (player.getScore() < current.getScore()) {
            //Ingresar a la izquierda
            if (current.getLeft() == null) {
                current.setLeft(player);
            } else {
                add(current.getLeft(), player);
            }
        } else if (player.getScore() > current.getScore()) {
            //Ingresar a la derecha
            if (current.getRight() == null) {
                current.setRight(player);
            } else {
                add(current.getRight(), player);
            }
        } else {
            //No hacer nada
        }
    }

    public void reverseInOrder() {
        System.out.printf("%10s %22s", "PLAYER", "SCORE");
        System.out.println();
        reverseInOrder(root);
    }

    private void reverseInOrder(Player current) {
        if (current == null) {
            return;
        }
        reverseInOrder(current.getRight());
        System.out.format("%10s %22s", current.getName(), df.format(current.getScore()));
        System.out.println();
        reverseInOrder(current.getLeft());
    }

}
