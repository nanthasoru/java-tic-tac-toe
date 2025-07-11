package game;

import javax.swing.JFrame;

import game.players.*;

public class GameHandler {

    public final static int PvP = 0, PvE = 1;
    
    private final TicTacToe game;
    private final int gamemode;
    private final Player player1, player2;

    public GameHandler(int gamemode)
    {
        this.gamemode = gamemode;
        this.game = new TicTacToe(gamemode);

        if (gamemode == PvP)
        {
            this.player1 = new Player(this.game);
            this.player2 = new Player(this.game);
        }
        else if (gamemode == PvE)
        {
            this.player1 = new Player(this.game);
            this.player2 = new Bot(this.game);
        }
        else
        {
            throw new Error("Unknown gamemode : " + gamemode);
        }
    }

    public void startCommandLineInterface()
    {
        while (true)
        {
            System.out.println("\n" + game + "\n");

            if (game.winning() || game.numberOfOccupiedCells() == 9) break;

            Player toPlay = game.isP2turn() ? this.player2 : this.player1;
            int numberOfOccupiedCells = game.numberOfOccupiedCells();

            do {
                toPlay.play();
            } while (game.numberOfOccupiedCells() == numberOfOccupiedCells);
        }
    }
}
