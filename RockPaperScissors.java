import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command
public class RockPaperScissors implements Callable<Integer> {

  @Option(names = { "-n"} )
  private int numberOfGames = 1000;

  @Option(names = { "-p1"} )
  private String player1Agent = "AlwaysRock";

  @Option(names = { "-p2"} )
  private String player2Agent = "AlwaysPaper";

  public Winner determineWinner(HandShape p1, HandShape p2) {
    if (p1.beats() == p2)
      return Winner.PLAYER_ONE;
    else if (p2.beats() == p1)
      return Winner.PLAYER_TWO;
    else
      return Winner.DRAW;
  }

  public IAgent agentForName(String name) throws Exception {
    switch (name) {
      case "AlwaysRock":
        return new AlwaysRockAgent();
      case "AlwaysPaper":
        return new AlwaysPaperAgent();
    }

    throw new Exception(String.format("Unknown agent: %s", name));
  }

  @Override
  public Integer call() throws Exception {
    IAgent player1 = agentForName(player1Agent);
    IAgent player2 = agentForName(player2Agent);

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

    return 0;
  }

}
