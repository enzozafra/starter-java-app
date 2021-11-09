package com.zafra.starterapp.handlers.beta;

import com.zafra.starterapp.models.beta.BetaEventData;
import com.zafra.starterapp.models.beta.BetaRecord;
import com.zafra.starterapp.models.beta.BetaState;
import java.util.Map;
import org.joda.time.DateTime;

public class BetaInviteSentEventHandler implements BetaEventHandler {
  @Override public void handle(
      Map<String, Map<DateTime, BetaRecord>> botDetectorMap,
      Map<String, BetaRecord> recordMap,
      BetaEventData data) {
    var record = recordMap.get(data.getEmail());
    record.setBetaState(BetaState.INVITED);
  }

  @Override public void validateInput(BetaEventData data) {

  }
}
