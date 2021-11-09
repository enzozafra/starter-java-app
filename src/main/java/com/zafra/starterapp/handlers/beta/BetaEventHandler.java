package com.zafra.starterapp.handlers.beta;

import com.zafra.starterapp.models.beta.BetaEventData;
import com.zafra.starterapp.models.beta.BetaRecord;
import java.util.Map;
import org.joda.time.DateTime;

public interface BetaEventHandler {
  void handle(Map<String, Map<DateTime, BetaRecord>> botDetectorMap, Map<String, BetaRecord> recordMap, BetaEventData data);
  void validateInput(BetaEventData data);
}
