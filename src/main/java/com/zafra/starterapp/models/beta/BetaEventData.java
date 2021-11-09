package com.zafra.starterapp.models.beta;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class BetaEventData {
  private DateTime timestamp;
  private String email;
  private BetaEventType betaEventType;
}
