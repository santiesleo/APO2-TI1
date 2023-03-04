import java.util.Scanner;
public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        Board board = new Board();

        System.out.println("1. Jugar, 2. Salir");
        int option = sc.nextInt();
        if(option == 1){
            System.out.println("nombre");
            String name = sc.nextLine();
            System.out.println("Por favor ingrese la cantidad de filas que desea para el tablero");
            int filas = sc.nextInt();
            board.setRows(filas);
            System.out.println("Por favor ingrese la cantidad de columnas para el tablero");
            int columnas = sc.nextInt();
            board.setColumns(columnas);
            //añadir nodo de tablero
            int medidas = columnas*filas;
            for (int i = 0; i < medidas; i++){
                board.addNode(new Square(i + 1), medidas, columnas);

            }
            board.showBoard();
        }

    }

    private static String chronometer(int maxMin){
        int min = 0;
        int seconds = 0;
        for (min = 0; min < maxMin; min++){
            for(seconds = 0; seconds < 60; seconds++){
                System.out.println(min + ":" + seconds);
                delaySeconds();
            }
        }

        String time = min + ":" + seconds;

        return time;
    }

    private static void delaySeconds(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){}
    }


}

