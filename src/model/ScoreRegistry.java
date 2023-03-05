package model;

import model.Player;

public class ScoreRegistry {
    private Player root;

    public void add(Player player){
        if(root == null){
            root = player;
        }else{add(root, player);}
    }

    private void add(Player current, Player player){
        if(player.getName().compareTo(current.getName()) < 0){
            //Ingresar a la izquierda
            if(current.getLeft() == null){
                current.setLeft(player);
            }else{
                add(current.getLeft(), player);
            }
        }else if (player.getName().compareTo(current.getName()) > 0){
            //Ingresar a la derecha
            if(current.getRight() == null){
                current.setRight(player);
            }else {
                add(current.getRight(), player);
            }
        }else{
            //No hacer nada
        }
    }


}
