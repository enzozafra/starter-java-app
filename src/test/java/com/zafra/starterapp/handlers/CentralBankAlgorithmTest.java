package com.zafra.starterapp.handlers;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CentralBankAlgorithmTest {
  private CentralBankAlgorithm centralBankAlgorithm;

  @Before
  public void setup() {
    centralBankAlgorithm = new CentralBankAlgorithm();
  }

  @Test
  public void testAllMethods() {
    var transactions = List.of("AB1", "BA2");
    centralBankAlgorithm.processTransactions(transactions);
    assertThat(centralBankAlgorithm.netBalance()).isEqualTo(new int[]
        {1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    assertThat(centralBankAlgorithm.receiveAmts()).isEqualTo(new int[]
        {2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    assertThat(centralBankAlgorithm.sendAmts()).isEqualTo(new int[]
        {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  public void generateMinimumTransfers() {
    var transactions = List.of("AB1", "BA2", "BC1", "AC1", "DA1");
    centralBankAlgorithm.processTransactions(transactions);
    assertThat(centralBankAlgorithm.generateMinimumTransfers()).isEqualTo(List.of("BA2", "AC2", "DA1"));
    assertThat(centralBankAlgorithm.generateMinimumTransfers()).isEqualTo(centralBankAlgorithm.generateMinimumTransfersWithArray(transactions));
  }
}
