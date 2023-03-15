package model;

public class ScoreRegistry {

    private Player root;

    public void add(Player player){
        if(root == null){
            root = player;
        }else{add(root, player);}
    }

    private void add(Player current, Player player){
        if(player.getScore() < current.getScore()){
            //Ingresar a la izquierda
            if(current.getLeft() == null){
                current.setLeft(player);
            }else{
                add(current.getLeft(), player);
            }
        }else if (player.getScore() > current.getScore()){
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

    public void inOrder(){
        inOrder(root);
    }


    private void inOrder(Player current){
        if(current == null){
            //current.getKey();
            //System.out.println("No hay nada");
            return;
        }
        inOrder(current.getLeft());//Menor a    //inOrder(current.getRight()); Mayor a
        System.out.println(current.getName());   //System.out.println(current.getKey());
        inOrder(current.getRight());// Mayor    //inOrder(current.getLeft()); Menor
    }


}
