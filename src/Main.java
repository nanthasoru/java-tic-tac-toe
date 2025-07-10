import game.Bot;
import game.Coordinate;
import game.TicTacToe;
import java.util.Scanner;

public class Main {

    public static void cliPvP()
    {
        TicTacToe game = new TicTacToe();
        Scanner playerInput = new Scanner(System.in);

        int numberOfOccupiedCells = 0;

        while (!game.winning() && numberOfOccupiedCells != 9)
        {
            System.out.println("\n"+game+"\n");
            
            numberOfOccupiedCells = game.numberOfOccupiedCells();

            do {

                System.out.print("Coordinate : ");
                String input = playerInput.nextLine();

                try {
                    Coordinate cell = Coordinate.valueOf(input);
                    game.playAt(cell);
                } catch (IllegalArgumentException e) {}


            } while (numberOfOccupiedCells == game.numberOfOccupiedCells());

        }

        System.out.println("\n"+game+"\n");

        playerInput.close();
    }

    public static void cliPvE()
    {
        Bot bot = new Bot();
        TicTacToe game = bot.getGame();
        Scanner playerInput = new Scanner(System.in);

        int numberOfOccupiedCells = 0;

        while (!game.winning() && game.numberOfOccupiedCells() != 9)
        {
            
            if (game.isP2turn()) {
                bot.play();
            }
            
            else
            {
                numberOfOccupiedCells = game.numberOfOccupiedCells();
                System.out.println("\n"+game+"\n");
                do {
                    System.out.print("Coordinate : ");
                    String input = playerInput.nextLine();

                    try {
                        Coordinate cell = Coordinate.valueOf(input);
                        game.playAt(cell);
                    } catch (IllegalArgumentException e) {}


                } while (numberOfOccupiedCells == game.numberOfOccupiedCells());
            }

        }

        System.out.println("\n"+game+"\n");

        playerInput.close();
    }

    public static void main(String[] args)
    {
        cliPvE();
    }
}