package com.zafra.starterapp.handlers;

import com.zafra.starterapp.models.BankTransfer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CentralBankAlgorithm {
  private final static Character CENTRAL_BANK = 'A';

  private Map<Character, Integer> bankPayable;
  private Map<Character, Integer> bankReceivable;
  private Map<Character, Integer> bankNetBalance;

  public CentralBankAlgorithm() {
    this.bankPayable = new HashMap<>();
    this.bankReceivable = new HashMap<>();
    this.bankNetBalance = new HashMap<>();
  }

  public void processTransactions(List<String> bankTransferStrings) {
    processTransactionObjects(parseBankTransfers(bankTransferStrings));
  }

  private void processTransactionObjects(List<BankTransfer> bankTransfers) {
    for (BankTransfer bankTransfer : bankTransfers) {
      char fromBank = bankTransfer.getFromBank();
      char toBank = bankTransfer.getToBank();
      int amount = bankTransfer.getAmount();

      int currPayable = bankPayable.getOrDefault(fromBank, 0);
      bankPayable.put(fromBank, currPayable + amount);
      int payableBankNetBalance = bankNetBalance.getOrDefault(fromBank, 0);
      bankNetBalance.put(fromBank, payableBankNetBalance - amount);

      int currReceivable = bankReceivable.getOrDefault(toBank, 0);
      bankReceivable.put(toBank, currReceivable + amount);
      int receivableBankNetBalance = bankNetBalance.getOrDefault(toBank, 0);
      bankNetBalance.put(toBank, receivableBankNetBalance + amount);
    }
  }

  private List<BankTransfer> parseBankTransfers(List<String> bankTransferStrings) {
    return bankTransferStrings.stream()
        .map(BankTransfer::of)
        .collect(Collectors.toUnmodifiableList());
  }

  public List<String> generateMinimumTransfers() {
    List<BankTransfer> bankTransfers = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : bankNetBalance.entrySet()) {
      char bank = entry.getKey();
      if (bank == CENTRAL_BANK) {
        continue;
      }

      int netBalance = entry.getValue();

      if (netBalance > 0) {
        bankTransfers.add(new BankTransfer(CENTRAL_BANK, bank, netBalance));
      } else if (netBalance < 0) {
        bankTransfers.add(new BankTransfer(bank, CENTRAL_BANK, Math.abs(netBalance)));
      }
    }

    return bankTransfers.stream()
        .map(BankTransfer::toString)
        .collect(Collectors.toUnmodifiableList());
  }

  public List<String> generateMinimumTransfersWithArray(List<String> transferDetailsString) {
    return generateMinimumTransfersWithArrayObject(parseBankTransfers(transferDetailsString));
  }

  private List<String> generateMinimumTransfersWithArrayObject(List<BankTransfer> transferDetails) {
    List<BankTransfer> transfersList = new ArrayList<>();
    if (transferDetails == null || transferDetails.size() == 0) {
      return List.of();
    }

    int[] sendAmt = new int[26];
    int[] receiveAmt = new int[26];
    int[] netBalance = new int[26];

    for (BankTransfer t : transferDetails) {
      int srcIdx = t.getFromBank() - 'A';
      int destIdx = t.getToBank() - 'A';
      int amount = t.getAmount();

      sendAmt[srcIdx] += amount;
      receiveAmt[destIdx] += amount;
    }

    for (int i = 1; i < 26; i++) {
      char bank = (char) (i + 'A');

      int netTransfer = receiveAmt[i] - sendAmt[i];
      netBalance[i] = netTransfer;

      if (netBalance[i] > 0) {
        transfersList.add(new BankTransfer(CENTRAL_BANK, bank, netTransfer));
      } else if (netBalance[i] < 0) {
        transfersList.add(new BankTransfer(bank, CENTRAL_BANK, Math.abs(netTransfer)));
      }
    }

    return transfersList.stream()
        .map(BankTransfer::toString)
        .collect(Collectors.toUnmodifiableList());
  }

  public int[] sendAmts() {
    int[] result = new int[26];
    for (Map.Entry<Character, Integer> entry : bankPayable.entrySet()) {
      char bank = entry.getKey();
      int toSend = entry.getValue();

      int key = bank - 'A';
      result[key] = toSend;
    }

    return result;
  }

  public int[] receiveAmts() {
    int[] result = new int[26];
    for (Map.Entry<Character, Integer> entry : bankReceivable.entrySet()) {
      char bank = entry.getKey();
      int toReceive = entry.getValue();

      int key = bank - 'A';
      result[key] = toReceive;
    }

    return result;
  }

  public int[] netBalance() {
    int[] result = new int[26];
    for (Map.Entry<Character, Integer> entry : bankNetBalance.entrySet()) {
      char bank = entry.getKey();
      int netAmount = entry.getValue();

      int key = bank - 'A';
      result[key] = netAmount;
    }

    return result;
  }
}
