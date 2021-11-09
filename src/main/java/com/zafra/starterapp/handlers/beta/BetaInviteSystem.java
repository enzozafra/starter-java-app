package com.zafra.starterapp.handlers.beta;

import com.zafra.starterapp.models.beta.BetaEventData;
import com.zafra.starterapp.models.beta.BetaEventType;
import com.zafra.starterapp.models.beta.BetaEventsResponse;
import com.zafra.starterapp.models.beta.BetaRecord;
import com.zafra.starterapp.models.beta.BetaState;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class BetaInviteSystem {
  private Map<String, Map<DateTime, BetaRecord>> botDetectorMap;
  private Map<String, BetaRecord> betaRecordsMap;
  private Map<BetaEventType, BetaEventHandler> eventHandlers;

  public BetaInviteSystem() {
    this.botDetectorMap = new HashMap<>();
    this.betaRecordsMap = new HashMap<>();

    this.eventHandlers = Map.of(
        BetaEventType.INVITE_ACTIVATED, new BetaInviteActivatedEventHandler(),
        BetaEventType.INVITE_REQUESTED, new BetaInviteRequestedEventHandler(),
        BetaEventType.INVITE_SENT, new BetaInviteSentEventHandler()
    );
  }

  public BetaEventsResponse handleEvents(List<String> events) {
    return handleEventsParsed(parseStringEvents(events));
  }

  public BetaEventsResponse handleEventsParsed(List<BetaEventData> events) {
    events.stream()
        .forEach(event -> {
          var handler = eventHandlers.get(event.getBetaEventType());
          handler.validateInput(event);
          handler.handle(botDetectorMap, betaRecordsMap, event);
        });

    return BetaEventsResponse.builder()
        .numberOfBots(calculateNumberOfBots())
        .averageTimeToActivate(calculateAverageTimeToActivate())
        .build();
  }

  private List<BetaEventData> parseStringEvents(List<String> events) {
    return events.stream()
        .map(event -> {
          var split = event.split(",");
          return BetaEventData.builder()
              .timestamp(new DateTime(Long.parseLong(split[0])))
              .betaEventType(BetaEventType.valueOf(StringUtils.toRootUpperCase(split[1])))
              .email(split[2])
              .build();
        })
        .collect(toImmutableList());
  }

  public int calculateNumberOfBots() {
    int numBots = 0;

    for (Map.Entry<String, Map<DateTime, BetaRecord>> entry : botDetectorMap.entrySet()) {
      var timeToRecordMap = entry.getValue();

      for (Map.Entry<DateTime, BetaRecord> timeToRecordEntry : timeToRecordMap.entrySet()) {
        var record = timeToRecordEntry.getValue();
        if (record.getInviteRequestCount() >= 5) {
          numBots += 1;
        }
      }
    }
    return numBots;
  }

  public int calculateAverageTimeToActivate() {
    OptionalDouble averageTimeToCalculate = betaRecordsMap.entrySet().stream()
        .map(Map.Entry::getValue)
        .filter(betaRecord -> betaRecord.getBetaState().equals(BetaState.ACTIVATED))
        .mapToInt(betaRecord -> betaRecord.calculateTimeToActivate())
        .average();

    if (averageTimeToCalculate.isPresent()) {
      return (int) Math.round(averageTimeToCalculate.getAsDouble());
    } else {
      return -1;
    }
  }
}
