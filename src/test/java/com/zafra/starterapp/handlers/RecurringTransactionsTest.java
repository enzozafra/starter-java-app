package com.zafra.starterapp.handlers;

import com.zafra.starterapp.models.Transaction;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RecurringTransactionsTest {
  private RecurringTransactions recurringTransactions;

  @Before
  public void setup() {
    recurringTransactions = new RecurringTransactions();
  }

  @Test
  public void getCompaniesWithRecurringTransactions() {
    var transactions = List.of(
        new Transaction("Netflix", 9.99, 0),
        new Transaction("Netflix", 9.99, 10),
        new Transaction("Netflix", 9.99, 20),
        new Transaction("Netflix", 9.99, 30),
        new Transaction("Amazon", 27.12, 32),
        new Transaction("Sprint", 50.11, 45),
        new Transaction("Sprint", 50.11, 55),
        new Transaction("Sprint", 50.11, 65),
        new Transaction("Sprint", 60.13, 77),
        new Transaction("Netflix", 9.99, 50));
    assertThat(recurringTransactions.getCompanyWithRecurringTransactions(transactions))
        .isEqualTo(List.of("Netflix", "Sprint"));
  }

  @Test
  public void getCompaniesWithRecurringTransactions_edgeCase() {
    var transactions = List.of(
        new Transaction("Netflix", 9.99, 0),
        new Transaction("Netflix", 9.99, 10),
        new Transaction("Netflix", 9.99, 15),
        new Transaction("Netflix", 9.99, 20),
        new Transaction("Amazon", 27.12, 32),
        new Transaction("Sprint", 50.11, 45),
        new Transaction("Sprint", 50.11, 55),
        new Transaction("Sprint", 50.11, 65),
        new Transaction("Sprint", 60.13, 77),
        new Transaction("Netflix", 9.99, 50));
    assertThat(recurringTransactions.getCompanyWithRecurringTransactions(transactions))
        .isEqualTo(List.of("Netflix", "Sprint"));
  }
}
