public class Program {
    public static Winner determineWinner(HandShape p1, HandShape p2) {
        if (p1.beats() == p2)
            return Winner.PLAYER_ONE;
        else if (p2.beats() == p1)
            return Winner.PLAYER_TWO;
        else
            return Winner.DRAW;
    }

    public static void main(String[] args) {
        IAgent player1 = new StrategyChangingAgent();
        IAgent player2 = new AlwaysPaperAgent();

        int numberOfGames = 1000;

        while (numberOfGames > 0) {
            HandShape p1choice = player1.nextMove();
            HandShape p2choice = player2.nextMove();

            switch (determineWinner(p1choice, p2choice)) {
                case PLAYER_ONE:
                    System.out.println("Player 1 wins!");
                    break;
                case PLAYER_TWO:
                    System.out.println("Player 2 wins!");
                    break;
                case DRAW:
                    System.out.println("It's a draw!");
                    break;
            }
            numberOfGames--;
        }
    }
}
