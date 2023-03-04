public class Board {

    private Square head;
    private Square tail;
    private int columns;
    private int rows;
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

    public void addNode(Square node, int medidas, int columnas){
        if(head==null){
            head = node;
            tail = node;
        }else{
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
            if((tail.getNum())==medidas){
                addColumAndRow(head, columnas);
            }
        }
    }
    private void addColumAndRow(Square current, int columnas){
        if(current==null){
            return;
        }
        if(current.getPrevious()!=null){
            if(current.getPrevious().getColumn()==columnas){
                columnsT=1;
                rowsT++;
            }
        }
        current.setColumn(columnsT++);
        current.setRow(rowsT);
        //System.out.println("["+current.getNum()+"] "+current.getColumn()+" "+current.getRow());
        addColumAndRow(current.getNext(), columnas);
    }
    public void showBoard() {
        showBoard(tail, getRows(), getColumns());

    }

    private void showBoard(Square current, int rowCount, int columnCount) {
        if (current != null && rowCount > 0) {
            if (current.getNum() % (columnCount*2) == 0) {
                System.out.println();
                current = showBoardRow(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            } else {
                current = showBoardRowInvested(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            }
        }
    }

    private Square showBoardRow(Square current, int columnCount) { //Imprime de menor a mayor (ej: 1, 2, 3, 4, 5)
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            System.out.print("[" + current.getNum() + "] ");
            lastNode = showBoardRow(current.getPrevious(), columnCount - 1);
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private Square showBoardRowInvested(Square current, int columnCount) { //Imprime de mayor a menor (ej: 5, 4, 3, 2, 1)
        Square lastNode = null;
        if (current != null && columnCount > 0) {
            lastNode = showBoardRowInvested(current.getPrevious(), columnCount - 1);
            System.out.print("[" + current.getNum() + "] ");
        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }





}