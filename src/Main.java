import game.GameHandler;

public class Main {

    public static void main(String[] args)
    {
        GameHandler game = new GameHandler(GameHandler.PvE);
        game.startCommandLineInterface();
    }
}