import java.util.ArrayList;
import java.util.List;

public class MemoryGameOutput implements IGameOutput {
  private List<Winner> winners;

  public List<Winner> getWinners() {
    return this.winners;
  }

  public MemoryGameOutput() {
    this.winners = new ArrayList<Winner>();
  }

  @Override
  public void ReportOutcome(Winner winner) {
    this.winners.add(winner);
  }

}
