import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
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

  public RockPaperScissors(List<CustomAgent> agents) {
    this.agents.put("AlwaysRock", new AlwaysRockAgent());
    this.agents.put("AlwaysScissors", new AlwaysScissorsAgent());
    this.agents.put("AlwaysPaper", new AlwaysPaperAgent());
    this.agents.put("StrategyChanging", new StrategyChangingAgent());

    for (CustomAgent customAgent : agents) {
      this.agents.put(customAgent.getName(), customAgent);
    }
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

  public Winner play(BaseAgent player1, BaseAgent player2) {
    HandShape p1choice = player1.nextMove();
    HandShape p2choice = player2.nextMove();

    var winner = determineWinner(p1choice, p2choice);

    if (winner == Winner.PLAYER_ONE) {
      player1.registerWin();
    } else if (winner == Winner.PLAYER_TWO) {
      player2.registerWin();
    }

    return winner;
  }

  public void play(IGameOutput output, int numberOfGames, BaseAgent player1, BaseAgent player2) {
    while (numberOfGames > 0) {
      output.ReportOutcome(play(player1, player2));
      numberOfGames--;
    }
  }

  @Override
  public Integer call() throws Exception {
    var sessionFactory = new Configuration()
        .addAnnotatedClass(CustomAgent.class)
        .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://postgres/")
        .setProperty(JAKARTA_JDBC_USER, "postgres")
        .setProperty(JAKARTA_JDBC_PASSWORD, "example")
        .setProperty(SHOW_SQL, "true")
        .setProperty(FORMAT_SQL, "true")
        .setProperty(HIGHLIGHT_SQL, "true")
        .buildSessionFactory();

    sessionFactory.getSchemaManager().exportMappedObjects(true);

    var app = Javalin.create();
    app.get("/", ctx -> {
      BaseAgent player1 = null;
      BaseAgent player2 = null;

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
    app.put("/agents", ctx -> {
      ObjectMapper mapper = new ObjectMapper();
      CustomAgent agent = mapper.readValue(ctx.body(), CustomAgent.class);
      this.agents.put(agent.getName(), agent);
      ctx.status(HttpStatus.CREATED);
    });
    app.start(8080);

    return 0;
  }

}
