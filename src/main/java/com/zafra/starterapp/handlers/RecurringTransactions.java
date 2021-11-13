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

  public boolean hasRecurringTransactions(List<Transaction> transactions) {
    if (transactions.size() < 3) {
      return false;
    }

    Map<String, Integer> numOccurenceMap = new HashMap<>();

    int prevTimestamp = transactions.get(0).getTimestamp();

    for (int i = 1; i < transactions.size(); i++) {
      var transaction = transactions.get(i);
      int numOccurence = numOccurenceMap.getOrDefault(transaction.getKey(prevTimestamp), 0);

      if (i == 1) {
        numOccurence += 1;
      }

      numOccurenceMap.put(transaction.getKey(prevTimestamp), numOccurence + 1);
      prevTimestamp = transaction.getTimestamp();
    }

    return numOccurenceMap.values().stream()
        .anyMatch(occurence -> occurence >= 3);
  }
}
