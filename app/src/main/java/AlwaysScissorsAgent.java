public class AlwaysScissorsAgent extends BaseAgent {
  public HandShape nextMove() {
    return HandShape.SCISSORS;
  }

  public String getName() {
    return "AlwaysScissorsAgent";
  }
}
