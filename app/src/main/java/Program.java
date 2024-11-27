import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

import picocli.CommandLine;

public class Program {
  private static List<CustomAgent> loadAgents() {
    File agentsConfig = new File("config/");

    var agents = new LinkedList<CustomAgent>();

    if (agentsConfig.isDirectory()) {

      var files = agentsConfig.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.toLowerCase().endsWith(".json");
        }
      });

      for (File file : files) {
        try {
          agents.add(CustomAgent.fromFile(file.getAbsolutePath()));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }

    return agents;
  }

  public static void main(String[] args) {
    var agents = loadAgents();
  }
}
