import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import picocli.CommandLine;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;

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

  private static void initPrometheus() throws IOException {
    JvmMetrics.builder().register();

    HTTPServer.builder().port(9040).buildAndStart();
  }

  public static void main(String[] args) {
    try {
      initPrometheus();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    var agents = loadAgents();
    new CommandLine(new RockPaperScissors(agents)).execute(args);
  }
}
