package com.zafra.starterapp.handlers.server;

import com.zafra.starterapp.models.server.Request;
import com.zafra.starterapp.models.server.Server;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ServerLoad {
  private final PriorityQueue<Server> availableServers;
  private final PriorityQueue<Server> busyServers;

  public ServerLoad() {
    this.availableServers = new PriorityQueue<>(new Server.ServerLoadComparator());
    this.busyServers = new PriorityQueue<>(new Server.ServerTimeReadyComparator());
  }

  public void loadServers(List<Integer> serverLoads) {
    for (int i = 0; i < serverLoads.size(); i++) {
      availableServers.add(new Server(String.format("Server %s", i), serverLoads.get(i), -1));
    }
  }

  public Map<Request, Server> acceptRequests(List<Request> requests) {
    Map<Request, Server> result = new HashMap<>();
    for (Request request : requests) {
      int currentTime = request.getTimestamp();
      freeUpUnusedServers(currentTime);

      if (100 - this.availableServers.peek().getLoad() >= request.getWeight()) {
        Server serverToUse = this.availableServers.poll();
        serverToUse.setTimeReady(currentTime + request.getTtl());
        this.busyServers.add(serverToUse);
        result.put(request, serverToUse);
      } else {
        result.put(request, new Server("NULL", -1, -1));
      }
    }

    return result;
  }

  public void freeUpUnusedServers(int currentTime) {
    while (!busyServers.isEmpty() && busyServers.peek().getTimeReady() <= currentTime) {
      Server freedUpServer = busyServers.remove();
      this.availableServers.add(freedUpServer);
    }
  }
}
