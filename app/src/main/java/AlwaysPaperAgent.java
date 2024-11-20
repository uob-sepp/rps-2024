public class AlwaysPaperAgent extends BaseAgent {
  public HandShape nextMove() {
    return HandShape.PAPER;
  }

  public String getName() {
    return "AlwaysPaperAgent";
  }
}
