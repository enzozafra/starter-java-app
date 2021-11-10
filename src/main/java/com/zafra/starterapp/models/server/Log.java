package com.zafra.starterapp.models.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Log {
  public boolean isDown;
  public int time;

  public int getPenalty(int removedAt) {
    if (removedAt <= time && !isDown ||
        removedAt > time && isDown) {
      return 1;
    }

    return 0;
  }
}
