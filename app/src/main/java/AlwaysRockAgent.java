public class AlwaysRockAgent extends BaseAgent {
  public HandShape nextMove() {
    return HandShape.ROCK;
  }

  public String getName() {
    return "AlwaysRockAgent";
  }
}
