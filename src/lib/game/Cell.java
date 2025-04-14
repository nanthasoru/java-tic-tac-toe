package lib.game;

final class Cell {

    /* Class attributes */
    
    // Both states of a cell in tic tac toe, null value if cell is blank
    static enum State { X, O }

    // Storing cell's state
    private State cellState;

    /* Constructor */

    Cell() { reset(); }

    void reset() { cellState = null; }

    /* Getters, Setters */

    State getState() { return cellState; }

    /* Methods */

    boolean canBeModified() { return cellState == null; }

    boolean updateTo(boolean isX) {
        if (cellState != null) return false;
        else {
            cellState = isX ? State.X : State.O;
            return true;
        }
    }

}
