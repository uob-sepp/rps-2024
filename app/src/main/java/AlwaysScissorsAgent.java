public class AlwaysScissorsAgent implements IAgent {
    public HandShape nextMove() {
        return HandShape.SCISSORS;
    }

    public String getName() {
        return "AlwaysScissorsAgent";
    }
}
