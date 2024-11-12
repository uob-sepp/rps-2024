public class AlwaysPaperAgent implements IAgent {
    public HandShape nextMove() {
        return HandShape.PAPER;
    }

    public String getName() {
        return "AlwaysPaperAgent";
    }
}
