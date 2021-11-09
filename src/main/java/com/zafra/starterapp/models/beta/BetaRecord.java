package com.zafra.starterapp.models.beta;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class BetaRecord {
  private DateTime requestedTime;
  private DateTime activatedTime;
  private BetaState betaState;
  private int inviteRequestCount;
  private String email;

  public int calculateTimeToActivate() {
    if (activatedTime == null || requestedTime == null) {
      return -1;
    }

    return (int)(activatedTime.getMillis() - requestedTime.getMillis());
  }
}
