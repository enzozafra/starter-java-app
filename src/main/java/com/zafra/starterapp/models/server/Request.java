package com.zafra.starterapp.models.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {
  private int weight;
  private int ttl;
  private int timestamp;
}
