public interface IAgent {
  public HandShape nextMove();

  public String getName();

  public void registerWin();

  public int getWins();
}
