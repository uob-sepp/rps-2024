import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "custom_agent")
@NamedQuery(name = "getAllAgents", query = "SELECT a FROM CustomAgent a")
public class CustomAgent extends BaseAgent {
  @Id
  private String name;
  private double rockProbability;
  private double paperProbability;
  private double scissorsProbability;
  @Transient
  private double totalWeight;

  private CustomAgent() {
  }

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
