import picocli.CommandLine;

public class Program {
  public static void main(String[] args) {
    new CommandLine(new RockPaperScissors()).execute(args);
  }
}
