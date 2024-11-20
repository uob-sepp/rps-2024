public abstract class BaseAgent implements IAgent {
  private int wins = 0;

  @Override
  public void registerWin() {
    this.wins++;
  }

  @Override
  public int getWins() {
    return this.wins;
  }
}
