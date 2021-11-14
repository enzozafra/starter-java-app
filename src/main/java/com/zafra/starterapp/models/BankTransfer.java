package com.zafra.starterapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankTransfer {
  private Character fromBank;
  private Character toBank;
  private int amount;

  public static BankTransfer of(String bankTransferString) {
    char fromBank = bankTransferString.charAt(0);
    char toBank = bankTransferString.charAt(1);
    int amount = Integer.parseInt(bankTransferString.substring(2));

    return new BankTransfer(fromBank, toBank, amount);
  }

  @Override
  public String toString() {
    return String.format("%s%s%s", fromBank, toBank, amount);
  }
}
