package com.zafra.starterapp.handlers.beta;

import com.zafra.starterapp.models.beta.BetaEventData;
import com.zafra.starterapp.models.beta.BetaRecord;
import com.zafra.starterapp.models.beta.BetaState;
import java.util.Map;
import org.joda.time.DateTime;

public class BetaInviteActivatedEventHandler implements BetaEventHandler {
  @Override public void handle(
      Map<String, Map<DateTime, BetaRecord>> botDetectorMap,
      Map<String, BetaRecord> recordMap,
      BetaEventData data) {
    var record = recordMap.get(data.getEmail());
    record.setBetaState(BetaState.ACTIVATED);
    record.setActivatedTime(data.getTimestamp());
  }

  @Override public void validateInput(BetaEventData data) {

  }
}
