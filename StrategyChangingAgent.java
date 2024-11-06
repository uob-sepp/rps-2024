import java.util.concurrent.*;

public class StrategyChangingAgent implements IAgent {
    private ThreadLocalRandom random;
    private IAgent agent;

    public StrategyChangingAgent() {
        this.random = ThreadLocalRandom.current();
        this.agent = new AlwaysRockAgent();
    }

    public HandShape nextMove() {
        int n = this.random.nextInt(0,8);

        if(n == 0) {
            n = this.random.nextInt(0,3);

            switch(n) {
                case 0: this.agent = new AlwaysRockAgent(); break;
                case 1: this.agent = new AlwaysPaperAgent(); break;
                case 2: this.agent = new AlwaysScissorsAgent(); break;
            }

            System.out.println("Changed to: " + this.agent.getName());
        }

        return this.agent.nextMove();
    }

    public String getName() {
        return "StrategyChangingAgent";
    }
}
