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
}
