import lib.game.Grid;

public final class App {

    /* Pretty useless class, does 2 things */
    public static void main(String[] args) {
        Grid twoPlayerGrid = new Grid();
        while (twoPlayerGrid.updateBoard());
    }
}
