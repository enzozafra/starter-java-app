package com.zafra.starterapp.handlers.capital;

import com.zafra.starterapp.models.capital.CapitalResult;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StripeCapitalTest {
  private StripeCapital stripeCapital;

  @Before
  public void setup() {
    this.stripeCapital = new StripeCapital();
  }

  @Test
  public void singleMerchant_singleLoan() {
    List<String> input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,5000");
    input.add("PAY_LOAN: acct_foobar,loan1,1000");

    var result = stripeCapital.handleActions(input);
    var expected = List.of(new CapitalResult("acct_foobar", 4000));
    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  public void singleMerchant_doubleLoan() {
    List<String> input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,5000");
    input.add("CREATE_LOAN: acct_foobar,loan2,5000");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan1,500,10");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan2,500,1");

    var result = stripeCapital.handleActions(input);
    var expected = List.of(new CapitalResult("acct_foobar", 9945));
    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  public void twoMerchants() {
    List<String> input = new ArrayList<>();
    input.add("CREATE_LOAN: acct_foobar,loan1,1000");
    input.add("CREATE_LOAN: acct_foobar,loan2,2000");
    input.add("CREATE_LOAN: acct_barfoo,loan1,3000");
    input.add("TRANSACTION_PROCESSED: acct_foobar,loan1,100,1");
    input.add("PAY_LOAN: acct_barfoo,loan1,1000");
    input.add("INCREASE_LOAN: acct_foobar,loan2,1000");

    var result = stripeCapital.handleActions(input);
    var expected = List.of(
        new CapitalResult("acct_foobar", 3999),
        new CapitalResult("acct_barfoo", 2000));
    assertThat(result).containsExactlyElementsOf(expected);
  }
}
