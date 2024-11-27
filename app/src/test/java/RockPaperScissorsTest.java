import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class RockPaperScissorsTest {
  @Test
  public void testRockAgentAlwaysBeatsScissors() {
    // setup
    BaseAgent player1 = new AlwaysRockAgent();
    BaseAgent player2 = new AlwaysScissorsAgent();
    RockPaperScissors game = new RockPaperScissors(new LinkedList<CustomAgent>());
    MemoryGameOutput output = new MemoryGameOutput();

    // run
    game.play(output, 5, player1, player2);

    // verify
    for (Winner winner : output.getWinners()) {
      assertEquals(Winner.PLAYER_ONE, winner);
    }
  }
}
