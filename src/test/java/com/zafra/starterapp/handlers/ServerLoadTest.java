package com.zafra.starterapp.handlers;

import com.zafra.starterapp.handlers.server.ServerLoad;
import com.zafra.starterapp.models.server.Request;
import java.util.Comparator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static java.util.stream.Collectors.toList;

public class ServerLoadTest {
  private ServerLoad serverLoad;

  @Before
  public void setup() {
    this.serverLoad = new ServerLoad();
  }

  @Test
  public void loadServers() {
    var serverLoads = List.of(0, 2, 3, 11, 20, 50, 82, 90, 94, 100);
    var requests = List.of(
        new Request(10, 9, 1), // server 0 w/ load 0
        new Request(99, 1, 2), // null
        new Request(98, 5, 3), // server 1 w/ load 2
        new Request(50, 2, 4), // server 2 w/ load 3
        new Request(10, 1, 5), // server 3 w/ load 11
        new Request(10, 120, 6), // server 2 w. load 3
        new Request(10, 150, 7),  // Server 3
        new Request(10, 4, 9), // Server 1
        new Request(10, 1, 12) // Server 0
    );
    serverLoad.loadServers(serverLoads);
    var result = serverLoad.acceptRequests(requests);
    var servers = result.keySet()
        .stream()
        .sorted(Comparator.comparingInt(Request::getTimestamp))
        .map(key -> result.get(key))
        .collect(toList());
  }
}
