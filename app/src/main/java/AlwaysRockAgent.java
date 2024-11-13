public class AlwaysRockAgent implements IAgent {
    public HandShape nextMove() {
        return HandShape.ROCK;
    }

    public String getName() {
        return "AlwaysRockAgent";
    }
}
