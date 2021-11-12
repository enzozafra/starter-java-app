package com.zafra.starterapp.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockSymbol {
  private String description;
  private String displaySymbol;
  private String symbol;

  // TODO: should be an enum
  private String type;
}
