package com.zafra.starterapp.handlers.capital;

import com.zafra.starterapp.models.capital.Book;
import com.zafra.starterapp.models.capital.CapitalActionData;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class PayLoanHandler implements CapitalActionHandler {
  @Override public void handle(
      Map<String, Map<String, Book>> books,
      CapitalActionData data) {
    var merchantId = data.getMerchantId();
    var loanId = data.getLoanId();

    var book = books.get(merchantId).get(loanId);

    if (book.getRemainingLoan() < data.getAmount()) {
      book.setRemainingLoan(0);
    } else {
      book.setRemainingLoan(book.getRemainingLoan() - data.getAmount());
    }
  }

  @Override public void validateData(
      Map<String, Map<String, Book>> books, CapitalActionData data) throws Exception {
    if (StringUtils.isEmpty(data.getMerchantId())) {
      throw new Exception("merchant_id is a required field");
    }

    if (StringUtils.isEmpty(data.getLoanId())) {
      throw new Exception("loan_id is a required field");
    }

    if (data.getAmount() < 0) {
      throw new Exception("amount must be >= 0");
    }

    if (!books.containsKey(data.getMerchantId()) || !books.get(data.getMerchantId()).containsKey(data.getLoanId())) {
      throw new Exception(String.format("merchant %s does not have a loan", data.getMerchantId()));
    }
  }
}
