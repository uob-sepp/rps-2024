import java.util.HashMap;
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

  private HashMap<String, BaseAgent> agents = new HashMap<String, BaseAgent>();

  public RockPaperScissors() {
    this.agents.put("AlwaysRock", new AlwaysRockAgent());
    this.agents.put("AlwaysScissors", new AlwaysScissorsAgent());
    this.agents.put("AlwaysPaper", new AlwaysPaperAgent());
    this.agents.put("StrategyChanging", new StrategyChangingAgent());
  }

  public Winner determineWinner(HandShape p1, HandShape p2) {
    if (p1.beats() == p2)
      return Winner.PLAYER_ONE;
    else if (p2.beats() == p1)
      return Winner.PLAYER_TWO;
    else
      return Winner.DRAW;
  }

  public BaseAgent agentForName(String name) throws Exception {
    BaseAgent agent = this.agents.get(name);

    if (agent != null) {
      return agent;
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
    var app = Javalin.create();
    app.get("/", ctx -> {
      IAgent player1 = null;
      IAgent player2 = null;

      String p1 = ctx.queryParam("p1");
      if (p1 == null) {
        player1 = agentForName(player1Agent);
      } else {
        player1 = agentForName(p1);
      }

      String p2 = ctx.queryParam("p2");
      if (p2 == null) {
        player2 = agentForName(player2Agent);
      } else {
        player2 = agentForName(p2);
      }

      var output = new MemoryGameOutput();
      play(output, numberOfGames, player1, player2);
      ctx.json(output.getWinners());
    });
    app.get("/agents", ctx -> ctx.json(this.agents));
    app.start(8080);

    return 0;
  }

}
