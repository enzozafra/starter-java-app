package com.zafra.starterapp.handlers.beta;

import com.zafra.starterapp.models.beta.BetaEventData;
import com.zafra.starterapp.models.beta.BetaRecord;
import com.zafra.starterapp.models.beta.BetaState;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;

public class BetaInviteRequestedEventHandler implements BetaEventHandler {
  @Override public void handle(
      Map<String, Map<DateTime, BetaRecord>> botDetectorMap,
      Map<String, BetaRecord> recordMap,
      BetaEventData data) {
    var email = data.getEmail();
    var timestamp = data.getTimestamp();

    Map<DateTime, BetaRecord> dateTimeBetaRecordMap;
    if (botDetectorMap.containsKey(email)) {
      dateTimeBetaRecordMap = botDetectorMap.get(email);
    } else {
      dateTimeBetaRecordMap = new HashMap<>();
      botDetectorMap.put(email, dateTimeBetaRecordMap);
    }

    DateTime botDetectorKey = new DateTime(
        timestamp.getYear(),
        timestamp.getMonthOfYear(),
        timestamp.getDayOfMonth(),
        timestamp.getHourOfDay(),
        timestamp.getMinuteOfHour());

    if (dateTimeBetaRecordMap.containsKey(botDetectorKey)) {
      var record = dateTimeBetaRecordMap.get(botDetectorKey);
      record.setInviteRequestCount(record.getInviteRequestCount() + 1);
    } else {
      var newRecord = BetaRecord.builder()
          .betaState(BetaState.INVITE_REQUESTED)
          .requestedTime(timestamp)
          .email(email)
          .inviteRequestCount(1)
          .build();
      dateTimeBetaRecordMap.put(botDetectorKey, newRecord);
      recordMap.put(email, newRecord);
    }
  }

  @Override public void validateInput(BetaEventData data) {

  }
}
