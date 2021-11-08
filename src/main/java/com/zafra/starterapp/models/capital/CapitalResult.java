package com.zafra.starterapp.models.capital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CapitalResult {
  private String accountId;
  private int amount;

  @Override
  public String toString() {
    return String.format("%,%", accountId, amount);
  }
}

