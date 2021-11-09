package com.zafra.starterapp.handlers.server;

import com.google.common.annotations.VisibleForTesting;
import com.zafra.starterapp.models.server.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.NoArgsConstructor;

import static com.google.common.collect.ImmutableList.toImmutableList;

@NoArgsConstructor
public class ServerRemover {

  public int compute_penalty(String log, int remove_at) {
    return compute_penalty(parseLogs(log), remove_at);
  }

  private int compute_penalty(List<Log> logs, int remove_at) {
    int penalty = 0;

    for (int i = 0; i < logs.size(); i++) {
      var logObject = logs.get(i);

      if (remove_at <= logObject.getTime() && !logObject.isDown()
          || remove_at > logObject.getTime() && logObject.isDown()) {
        penalty += 1;
      }
    }

    return penalty;
  }

  public int bestTimeToRemoveServer(String logString) {
    return bestTimeToRemoveServer(parseLogs(logString));
  }

  private int bestTimeToRemoveServer(List<Log> logs) {
    int minPenalty = Integer.MAX_VALUE;
    int minTime = Integer.MAX_VALUE;

    // Brute force by testing every single time
    for (int i = 0; i < logs.size() + 1; i++) {
      int penalty = compute_penalty(logs, i);
      if (penalty < minPenalty) {
        minPenalty = penalty;
        minTime = i;
      }
    }

    return minTime;
  }

  public List<Integer> bestTimesToRemoveServerWithNestedLogs(String logString) {
    ArrayList<Integer> results = new ArrayList<>();
    boolean foundBegin = false;

    var logSplit = logString.split(" ");

    ArrayList<Log> currentLog = new ArrayList<>();
    int time = 0;

    for (int i = 0; i < logSplit.length; i++) {
      var log = logSplit[i];

      if (log.equals("BEGIN") && !foundBegin) {
        foundBegin = true;
        time = 1;
      } else if ((log.equals("1") || log.equals("0")) && foundBegin) {
        currentLog.add(Log.builder()
            .isDown(log.equals("1"))
            .time(time)
            .build());
        time += 1;
      } else if (log.equals("END") && foundBegin) {
        if (!currentLog.isEmpty()) {
          results.add(bestTimeToRemoveServer(currentLog));
        }
        currentLog = new ArrayList<>();
        foundBegin = false;
      }
    }

    return results;
  }

  @VisibleForTesting List<Log> parseLogs(String logString) {
    var splitLogString = logString.split(" ");
    return IntStream.range(0, splitLogString.length)
        .mapToObj(index -> {
          var log = splitLogString[index];
          return Log.builder()
              .isDown(log.equals("1"))
              .time(index + 1)
              .build();
        })
        .collect(toImmutableList());
  }
}
