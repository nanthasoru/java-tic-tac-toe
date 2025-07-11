package game.players;

import game.Coordinate;
import game.TicTacToe;

public class Player {

    protected final TicTacToe game;

    public Player(TicTacToe game) {
        this.game = game;
    }
    
    public void play()
    {
        System.out.print("Coordinate : ");
        String input = System.console().readLine();

        try
        {
            Coordinate cell = Coordinate.valueOf(input);
            game.playAt(cell.ordinal());
        }
        catch (IllegalArgumentException e)
        {}
    }
}
