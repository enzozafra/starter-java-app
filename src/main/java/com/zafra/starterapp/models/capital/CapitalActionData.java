package com.zafra.starterapp.models.capital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CapitalActionData {
  private String loanId;
  private String merchantId;
  private int amount;
  private int repaymentPercentage;
}
