package com.zafra.starterapp.models.server;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Server {
  private String name;
  private int load;
  private int timeReady;

  public static class ServerLoadComparator implements Comparator<Server> {
    @Override
    public int compare(Server server1, Server server2) {
      return server1.getLoad() - server2.getLoad();
    }
  }

  public static class ServerTimeReadyComparator implements Comparator<Server> {
    @Override
    public int compare(Server server1, Server server2) {
      return server1.getTimeReady() - server2.getTimeReady();
    }
  }

  @Override
  public String toString() {
    return String.format("name: %s, load: %s, timeReady: %s", name, load, timeReady);
  }
}
