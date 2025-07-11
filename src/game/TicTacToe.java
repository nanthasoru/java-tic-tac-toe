package game;

import java.util.ArrayList;
import java.util.Stack;

public class TicTacToe {

    private final Boolean[] grid;
    private boolean isP2turn;
    private Stack<Integer> moveHistory;

    public TicTacToe(int gamemode)
    {
        this.grid = new Boolean[9];
        this.isP2turn = false;
        this.moveHistory = new Stack<>();
    }

    public boolean isP2turn() {
        return isP2turn;
    }

    public int numberOfOccupiedCells()
    {
        int c = 0;

        for (Boolean val : grid) {
            if (val != null) c++;
        }

        return c;
    }

    public ArrayList<Integer> freeCellIndices()
    {

        ArrayList<Integer> free = new ArrayList<>();

        for (int i = 0; i < grid.length; i++)
        {
            if (grid[i] == null)
                free.add(i);
        }

        return free;
    }

    public void playAt(int i)
    {
        if (grid[i] == null) {
            grid[i] = isP2turn;
            isP2turn = !isP2turn;
            moveHistory.push(i);
        }
    }

    public void undo()
    {
        if (!moveHistory.isEmpty()) {
            isP2turn = !isP2turn;
            grid[moveHistory.pop()] = null;
        }
    }

    private boolean sameCells(Boolean b1, Boolean b2, Boolean b3) {
        return b1 == null || b2 == null || b3 == null ? false : b1 == b2 && b1 == b3;
    }

    public boolean winning()
    {
        return
            sameCells(grid[0], grid[1], grid[2]) ||
            sameCells(grid[3], grid[4], grid[5]) ||
            sameCells(grid[6], grid[7], grid[8]) ||
            sameCells(grid[0], grid[3], grid[6]) ||
            sameCells(grid[1], grid[4], grid[7]) ||
            sameCells(grid[2], grid[5], grid[8]) ||
            sameCells(grid[0], grid[4], grid[8]) ||
            sameCells(grid[2], grid[4], grid[6]) ;
    }

    @Override
    public String toString()
    {
        String gridRepresentation = "  ┌───┬───┬───┐\n";

        for (int rows = 0; rows < 3; rows ++)
        {
            gridRepresentation += "321".charAt(rows) + " │";

            for (int cols = 0; cols < 3; cols ++)
            {
                Boolean cell = this.grid[rows * 3 + cols];
                gridRepresentation += " " + (cell == null ? ' ' : (cell ? 'O' : 'X')) + " │";
            }

            gridRepresentation += "\n  " + (rows != 2 ? "├───┼───┼───┤\n" : "└───┴───┴───┘\n    a   b   c");
        }

        return gridRepresentation;
    }
}
