import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAgent extends BaseAgent {
  private String name;
  private double rockProbability;
  private double paperProbability;
  private double scissorsProbability;
  private double totalWeight;

  @JsonCreator
  public CustomAgent(@JsonProperty("name") String name, @JsonProperty("rockProbability") double rockProbability,
      @JsonProperty("paperProbability") double paperProbability,
      @JsonProperty("scissorsProbability") double scissorsProbability) {
    this.name = name;
    this.rockProbability = rockProbability;
    this.paperProbability = paperProbability;
    this.scissorsProbability = scissorsProbability;
    this.totalWeight = this.rockProbability + this.paperProbability + this.scissorsProbability;
  }

  @Override
  public HandShape nextMove() {
    var random = ThreadLocalRandom.current();
    var value = random.nextDouble() * this.totalWeight;

    if (value < this.rockProbability) {
      return HandShape.ROCK;
    } else if (value < this.rockProbability + this.paperProbability) {
      return HandShape.PAPER;
    } else {
      return HandShape.SCISSORS;
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  public static CustomAgent fromFile(String path) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(path);
    return mapper.readValue(file, CustomAgent.class);
  }
}
