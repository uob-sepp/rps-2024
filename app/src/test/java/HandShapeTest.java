import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HandShapeTest {
  @Test
  void beatsCorrectness() {
    assertEquals(HandShape.PAPER.beats(), HandShape.ROCK);
    assertEquals(HandShape.ROCK.beats(), HandShape.SCISSORS);
    assertEquals(HandShape.SCISSORS.beats(), HandShape.PAPER);
  }

  @Test
  void paperBeatsRock() {
    assertEquals(HandShape.PAPER.beats(), HandShape.ROCK);
  }
}
