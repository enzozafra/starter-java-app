package com.zafra.starterapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
  private String company;
  private double amount;
  private int timestamp;

  public String getKey(int prevTimestamp) {
    int difference = 0;
    if (prevTimestamp != -1) {
      difference = timestamp - prevTimestamp;
    }
    return String.format("%s-%s", amount, difference);
  }
}
