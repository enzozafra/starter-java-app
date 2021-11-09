package com.zafra.starterapp.models.beta;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class BetaEventsResponse {
  private final static String STRING_FORMAT = "%s %s";

  private int numberOfBots;
  private int averageTimeToActivate;

  @Override
  public String toString() {
    return String.format(STRING_FORMAT, numberOfBots, averageTimeToActivate);
  }
}
