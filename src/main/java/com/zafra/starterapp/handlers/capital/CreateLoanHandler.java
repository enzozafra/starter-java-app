package com.zafra.starterapp.handlers.capital;

import com.zafra.starterapp.models.capital.Book;
import com.zafra.starterapp.models.capital.CapitalActionData;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class CreateLoanHandler implements CapitalActionHandler {
  @Override public void handle(
      Map<String, Map<String, Book>> books,
      CapitalActionData data) {
    var merchantId = data.getMerchantId();
    var loanId = data.getLoanId();

    if (!books.containsKey(merchantId)) {
      books.put(merchantId, new HashMap<>());
    }

    books.get(merchantId).put(loanId, Book.builder()
        .merchantId(merchantId)
        .loanId(loanId)
        .remainingLoan(data.getAmount())
        .build());
  }

  @Override public void validateData(Map<String, Map<String, Book>> books, CapitalActionData data) throws Exception {
    if (StringUtils.isEmpty(data.getMerchantId())) {
      throw new Exception("merchant_id is a required field");
    }

    if (StringUtils.isEmpty(data.getLoanId())) {
      throw new Exception("loan_id is a required field");
    }

    if (data.getAmount() < 0) {
      throw new Exception("amount must be >= 0");
    }

    if (books.containsKey(data.getMerchantId()) && books.get(data.getMerchantId()).containsKey(data.getLoanId())) {
      throw new Exception(String.format("merchant %s already has loan %s",
          data.getMerchantId(), data.getLoanId()));
    }

  }
}
