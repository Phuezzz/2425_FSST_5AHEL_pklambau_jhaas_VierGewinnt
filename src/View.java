// View.java
public class View {
    public void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print("| " + cell + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------------------------");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
