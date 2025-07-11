package game.players;

import game.TicTacToe;

public final class Bot extends Player {

    public Bot(TicTacToe game) {
        super(game);
    }

    @Override
    public void play() {
        game.playAt(chooseCoordinate());
    }

    private int search()
    {
        if (game.winning())
            return game.isP2turn() ? -1 : 1;
        if (game.numberOfOccupiedCells() == 9)
            return 0;

        boolean isBot = game.isP2turn();
        int bestScore = isBot ? -1 : 1;

        for (int coordinate : game.freeCellIndices())
        {
            game.playAt(coordinate);
            int score = search();
            game.undo();
            bestScore = isBot ? Math.max(bestScore, score) : Math.min(score, bestScore);
        }

        return bestScore;
    }

    private int chooseCoordinate()
    {
        int bestScore = -1;
        
        for (int coordinate : game.freeCellIndices())
        {
            game.playAt(coordinate);
            float score = search();
            game.undo();

            if (score > bestScore)
                return coordinate;
        }

        return -1;
    }
}
