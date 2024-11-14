public class StdoutGameOutput implements IGameOutput {

  @Override
  public void ReportOutcome(Winner winner) {
    switch (winner) {
      case PLAYER_ONE:
        System.out.println("Player 1 wins!");
        break;
      case PLAYER_TWO:
        System.out.println("Player 2 wins!");
        break;
      case DRAW:
        System.out.println("It's a draw!");
        break;
    }
  }

}
