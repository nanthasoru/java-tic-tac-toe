package lib.game;
import lib.external.StdDraw;

public class Grid {
    
    /* Class attributes */

    private static final int LENGTH = 9; // 3x3 grid
    private static final int FRAMERATES = 1000/12; // 12 fps, who needs more ?

    private final Cell[] grid;
    private int gridPointer, turn, blueWinned, redWinned;

    /* Constructor */

    public Grid() {
        this.gridPointer = 0;
        this.turn = 0;
        this.blueWinned = 0;
        this.redWinned = 0;
        this.grid = new Cell[LENGTH];
        for (int i = 0 ; i < LENGTH ; i++)
            grid[i] = new Cell();
    }

    /* DRAW THE GRID */

    private void drawX(double x, double y) { // Draws an X
        double l = 0.1;
        StdDraw.line(x - l, y - l, x + l, y + l);
        StdDraw.line(x - l, y + l, x + l, y - l);
    }

    private void initDraw() { // draws the grid
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor();
        StdDraw.enableDoubleBuffering();
        StdDraw.line(0.33, 0, 0.33, 1);
        StdDraw.line(0.66, 0, 0.66, 1);
        StdDraw.line(0, 0.33, 1, 0.33);
        StdDraw.line(0, 0.66, 1, 0.66);
    }

    private void drawContent(Cell cell, double x, double y) { // draws an X, O or nothing depending on the cell
        if (cell == null || x > 1 || x < 0 || y > 1 || y < 0) {
            throw new IllegalArgumentException();
        }
        Cell.State state = cell.getState();
        switch (state) {
            case null:
                break;
            case Cell.State.X:
                StdDraw.setPenColor(StdDraw.RED);
                drawX(x, y);
                break;
            case Cell.State.O:
                StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                StdDraw.circle(x, y, 0.1);
                break;
            default:
                throw new Error(String.format("Cell.State.%s case isn't covered", state));
        }
    }

    private void drawGridContent() { // draws grid's content (X and O)
        StdDraw.clear();
        initDraw();
        double x = 0.33;
        double dec = x/2;
        double ced = 1 - x/2;
        for (int i = 0 ; i < LENGTH ; i++) {
            if (i % 3 == 0 && i != 0) {
                dec = x/2;
                ced -= x;
            }
            if (i == gridPointer) {
                StdDraw.setPenColor(StdDraw.SILVER);
                StdDraw.point(dec, ced);
            }
            drawContent(grid[i], dec, ced);
            dec += x;
        }
    }

    private void displayText(String arg) {
        StdDraw.clear();
        StdDraw.setPenColor();
        StdDraw.text(0.5, 0.5, arg);
        StdDraw.show();
    }

    private void displayScore() {
        StdDraw.clear();
        StdDraw.setPenColor();
        StdDraw.text(0.5, 0.5, "-");
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.text(0.3, 0.5, blueWinned + "");
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.7, 0.5, redWinned + "");
        StdDraw.show();
    }

    /* GRID METHODS */

    private void updateCellTo(int pos, boolean isX) { // update a cell's state from null to X or O
        pos--;
        if (pos > LENGTH || pos < 0) {
            throw new IllegalArgumentException();
        }
        if (grid[pos].updateTo(isX)) turn++;
    }

    public void reset() { // resets grid for a new game, to reset EVERYTHING just create a 'new Grid()'
        for (Cell cell : grid) cell.reset();
        this.gridPointer = 0;
        this.turn = 0;
    }

    private boolean areCellsSame(Cell val1, Cell val2, Cell val3) {
        for (Cell cell : new Cell[]{val1, val2, val3}) {
            if (cell == null) throw new IllegalArgumentException();
        }
        Cell.State state = val1.getState();
        if (state == null) return false;
        return state == val2.getState() && state == val3.getState();
    }

    private boolean hasAWinningLine() { // checks if someone won
        return (
            // Each lines -
            areCellsSame(grid[0], grid[1], grid[2]) ||
            areCellsSame(grid[3], grid[4], grid[5]) ||
            areCellsSame(grid[6], grid[7], grid[8]) ||
            // Each columns |
            areCellsSame(grid[0], grid[3], grid[6]) ||
            areCellsSame(grid[1], grid[4], grid[7]) ||
            areCellsSame(grid[2], grid[5], grid[8]) ||
            // Checks \ and /
            areCellsSame(grid[0], grid[4], grid[8]) ||
            areCellsSame(grid[2], grid[4], grid[6])
        );
    }

    /* USER'S KEY INPUT */

    private static enum Key { // Enum for key, makes code less messy to me
        LEFT(37),
        UP(38),
        RIGHT(39),
        DOWN(40),
        SPACEBAR(32),
        ESCAPE(27),
        R(82);

        private final int keycode;

        private Key(int keycode) {
            this.keycode = keycode;
        }
    } 

    private Key getPressedKey() {
        Key key = null;
        while (key == null) {
            if (StdDraw.isKeyPressed(Key.LEFT.keycode)) {        // LEFT
                key = Key.LEFT;
            } else if (StdDraw.isKeyPressed(Key.UP.keycode)) { // UP
                key = Key.UP;
            } else if (StdDraw.isKeyPressed(Key.RIGHT.keycode)) { // RIGHT
                key = Key.RIGHT;
            } else if (StdDraw.isKeyPressed(Key.DOWN.keycode)) { // DOWN
                key = Key.DOWN;
            } else if (StdDraw.isKeyPressed(Key.SPACEBAR.keycode)) { // SPACEBAR
                key = Key.SPACEBAR;
            } else if (StdDraw.isKeyPressed(Key.ESCAPE.keycode)) {
                key = Key.ESCAPE;
            } else if (StdDraw.isKeyPressed(Key.R.keycode)) {
                key = Key.R;
            }
            StdDraw.pause(FRAMERATES); 
        }
        return key;
    }

    private void evalKey(Key key) {
        if (key == null) return;
        switch (key) {
            case Key.LEFT:
                int calc1 = gridPointer - 1;
                gridPointer = (calc1 >= 0 && calc1 < LENGTH) ? calc1 : gridPointer;
                break;
            case Key.UP:
                int calc2 = gridPointer - 3;
                gridPointer = (calc2 >= 0 && calc2 < LENGTH) ? calc2 : gridPointer;
                break;
            case Key.RIGHT:
                int calc3 = gridPointer + 1;
                gridPointer = (calc3 >= 0 && calc3 < LENGTH) ? calc3 : gridPointer;
                break;
            case Key.DOWN:
                int calc4 = gridPointer + 3;
                gridPointer = (calc4 >= 0 && calc4 < LENGTH) ? calc4 : gridPointer;
                break;
            case Key.SPACEBAR:
                updateCellTo(gridPointer + 1, turn % 2 == 0);
                break;
            case Key.ESCAPE:
                displayText("Quitting");
                StdDraw.pause(2000);
                System.exit(0);
                break;
            case Key.R:
                displayText("Resetting..");
                StdDraw.pause(1000);
                reset();
                break;
            default:
                throw new Error(String.format("Key.%s case isn't covered", key));
        }
    }

    /* 'main' method */

    public boolean updateBoard() { // well...
        initDraw();
        StdDraw.show();
        while (turn < 4 || turn != 9 && !hasAWinningLine()) {
            evalKey(getPressedKey());
            drawGridContent();
            StdDraw.show();
        }
        StdDraw.pause(500);
        if (turn == 9) {
            displayText("What a waste of time, no one winned");
        } else {
            if (turn % 2 == 0) blueWinned++;
            else redWinned++;
            displayScore();
        }
        StdDraw.pause(2000);
        StdDraw.clear();
        reset();
        return true;
    }
}
