package com.zafra.starterapp.models.capital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {
  private String loanId;
  private String merchantId;
  private int remainingLoan;
}
