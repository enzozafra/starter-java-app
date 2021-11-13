import java.util.*;

/*

Part 1
Minimum 3 transactions are required to consider it as a recurring transaction
Same company, same amount, same number of days apart - recurring transactions

Input: Company, Amount, Timestamp (Day of the transaction)
Output: An array of companies who made recurring transactions

Part 2
The amounts and timestamps - similar
20% higher than minimum

(minimum of all amount) 10$ + 20% -> 12$
[10, 11, 12]

[
  ("Netflix", 9.99, 0),
  ("Netflix", 9.99, 10),
  ("Netflix", 9.99, 20),
  ("Netflix", 9.99, 30),
  ("Amazon", 27.12, 32),
  ("Sprint", 50.11, 45),
  ("Sprint", 50.11, 55),
  ("Sprint", 50.11, 65),
  ("Sprint", 60.13, 77),
  ("Netflix", 9.99, 50),
]

Map<String, List<Transaction>>

Transaction {
    double amount;
    int timestamp;
}

Sprint
[50, 50, 50, 60] ->

Amazon ->

Netflix
[9, 9, 9]
[10, 20, 30, 50] -> {10, 10, 20} ->

Number of items ->
Max timestamp -> Integer

 */
public class Solution {
  public static class Transaction {
    String company;
    double amount;
    int timestamp;

    public Transaction(String company, double amount, int timestamp) {
      this.company = company;
      this.amount = amount;
      this.timestamp = timestamp;
    }
  }

  public List<String> getCompaniesWithRecurringTransactions(Transaction[] transactions) {
    List<String> companies = new ArrayList<>();
    if (transactions == null || transactions.length == 0) {
      return companies;
    }

    Map<String, List<Transaction>> transactionsMap = new HashMap<>();
    for (Transaction t : transactions) {
      List<Transaction> listOfTransactions =
          transactionsMap.getOrDefault(t.company, new ArrayList<>());
      listOfTransactions.add(t);
      transactionsMap.put(t.company, listOfTransactions);
    }

    for (String company : transactionsMap.keySet()) {
      List<Transaction> listOfTransactions = transactionsMap.get(company);
      if (listOfTransactions.size() < 3) {
        continue;
      }

      if (hasRecurringTransactions(listOfTransactions)) {
        companies.add(company);
      }
    }

    return companies;
  }

  private boolean hasRecurringTransactions(List<Transaction> transactions) {
    double minAmount = getMinimumAmount(transactions);
    double maxAmount = minAmount + (minAmount * 0.2);

    int minDays = getMinimumDays(transactions);
    int maxDays = minDays + (int) (minDays * 0.2);

    int prevTimestamp = transactions.get(0).timestamp;

    for (int index = 0; index < transactions.size(); index++) {
      double curAmount = transactions.get(index).amount;
      if (minAmount > curAmount || curAmount > maxAmount) {
        return false;
      }

      if (index == 0) {
        continue;
      }

      int days = transactions.get(index).timestamp - prevTimestamp;
      if (days == 0 || days < minDays || days > maxDays) {
        return false;
      }

      prevTimestamp = transactions.get(index).timestamp;
    }

    return true;
  }

  private double getMinimumAmount(List<Transaction> transactions) {
    double minAmount = Double.MAX_VALUE;
    for (Transaction t : transactions) {
      minAmount = Math.min(t.amount, minAmount);
    }

    return minAmount;
  }

  private int getMinimumDays(List<Transaction> transactions) {
    int difference = Integer.MAX_VALUE;
    int prevTimestamp = transactions.get(0).timestamp;
    for (int i = 1; i < transactions.size(); i++) {
      difference = Math.min(difference, transactions.get(i).timestamp - prevTimestamp);
      prevTimestamp = transactions.get(i).timestamp;
    }

    return difference;
  }

  public static void main(String[] args) {
    Solution obj = new Solution();

    Transaction[] transactions = new Transaction[] {
        new Transaction("Netflix", 9.99, 10),
        new Transaction("Netflix", 9.99, 20),
        new Transaction("Netflix", 9.99, 30),
        new Transaction("Amazon", 27.12, 32),
        new Transaction("Sprint", 50.11, 45),
        new Transaction("Sprint", 50.11, 55),
        new Transaction("Sprint", 50.11, 65),
        new Transaction("Sprint", 60.13, 77)};

    List<String> result = obj.getCompaniesWithRecurringTransactions(transactions);
    System.out.println("The companies with recurring transactions are: " + result);
  }
}
