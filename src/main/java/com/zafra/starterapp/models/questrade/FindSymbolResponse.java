package com.zafra.starterapp.models.questrade;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindSymbolResponse {
  private int count;
  private List<StockSymbol> result;
}
