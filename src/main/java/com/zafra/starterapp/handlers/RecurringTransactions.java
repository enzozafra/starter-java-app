package com.zafra.starterapp.handlers;

import com.zafra.starterapp.models.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecurringTransactions {
  public RecurringTransactions() {
  }

  public List<String> getCompanyWithRecurringTransactions(List<Transaction> transactions) {
    Map<String, List<Transaction>> companyTransactionsMap = new HashMap<>();
    transactions.forEach(transaction -> {
      String company = transaction.getCompany();
      List<Transaction> companyTransactions =
          companyTransactionsMap.getOrDefault(company, new ArrayList<>());
      companyTransactionsMap.put(company, companyTransactions);
      companyTransactions.add(transaction);
    });

    return companyTransactionsMap.keySet().stream()
        .filter(company -> hasRecurringTransactions(companyTransactionsMap.get(company)))
        .collect(Collectors.toUnmodifiableList());
  }

  private boolean hasRecurringTransactions(List<Transaction> transactions) {
    if (transactions.size() < 3) {
      return false;
    }

    int minInterval = getMinimumInterval(transactions);
    int maxInterval = (int) (minInterval * 1.2);

    double minAmount = getMinimumAmount(transactions);
    double maxAmount = minAmount * 1.2;

    int prevTimestamp = transactions.get(0).getTimestamp();

    for (int i = 0; i < transactions.size(); i++) {
      var transaction = transactions.get(i);
      var currAmount = transaction.getAmount();

      if (currAmount > maxAmount || currAmount < minAmount) {
        return false;
      }

      if (i == 0) {
        continue;
      }

      var timestamp = transaction.getTimestamp();
      var currInterval = timestamp - prevTimestamp;
      prevTimestamp = timestamp;

      if (currInterval == 0 || currInterval > maxInterval || currInterval < minInterval) {
        return false;
      }
    }

    return true;
  }

  private boolean hasRecurringTransactionsWithoutMinMax(List<Transaction> transactions) {
    if (transactions.size() < 3) {
      return false;
    }

    int prevTimestamp = transactions.get(0).getTimestamp();
    double amount = transactions.get(0).getAmount();

    int interval = -1;

    for (int i = 1; i < transactions.size(); i++) {
      var transaction = transactions.get(i);
      var currAmount = transaction.getAmount();

      if (currAmount != amount) {
        return false;
      }

      var timestamp = transaction.getTimestamp();
      var currInterval = timestamp - prevTimestamp;
      prevTimestamp = timestamp;

      if (i == 1) {
        interval = currInterval;
        continue;
      }

      if (currInterval != interval) {
        return false;
      }
    }

    return true;
  }

  private double getMinimumAmount(List<Transaction> transactions) {
    return transactions.stream()
        .mapToDouble(Transaction::getAmount)
        .min()
        .getAsDouble();
  }

  private int getMinimumInterval(List<Transaction> transactions) {
    int minDifference = Integer.MAX_VALUE;
    int prevTimestamp = transactions.get(0).getTimestamp();

    for (int i = 1; i < transactions.size(); i++) {
      int timestamp = transactions.get(i).getTimestamp();
      minDifference = Math.min(timestamp - prevTimestamp, minDifference);
      prevTimestamp = timestamp;
    }

    return minDifference;
  }
}
