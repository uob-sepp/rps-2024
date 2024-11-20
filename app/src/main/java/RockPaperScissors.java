import java.util.concurrent.Callable;

import io.javalin.Javalin;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command
public class RockPaperScissors implements Callable<Integer> {

  @Option(names = { "-n" })
  private int numberOfGames = 1000;

  @Option(names = { "-p1" })
  private String player1Agent = "AlwaysRock";

  @Option(names = { "-p2" })
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

  public Winner play(IAgent player1, IAgent player2) {
    HandShape p1choice = player1.nextMove();
    HandShape p2choice = player2.nextMove();

    return determineWinner(p1choice, p2choice);
  }

  public void play(IGameOutput output, int numberOfGames, IAgent player1, IAgent player2) {
    while (numberOfGames > 0) {
      output.ReportOutcome(play(player1, player2));
      numberOfGames--;
    }
  }

  @Override
  public Integer call() throws Exception {
    IAgent player1 = agentForName(player1Agent);
    IAgent player2 = agentForName(player2Agent);

    var app = Javalin.create();
    app.get("/", ctx -> {
      var output = new MemoryGameOutput();
      play(output, numberOfGames, player1, player2);
      ctx.json(output.getWinners());
    });
    app.start(8080);

    return 0;
  }

}
