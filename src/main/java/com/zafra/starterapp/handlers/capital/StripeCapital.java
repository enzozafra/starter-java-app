package com.zafra.starterapp.handlers.capital;

import com.zafra.starterapp.models.capital.Book;
import com.zafra.starterapp.models.capital.CapitalActionData;
import com.zafra.starterapp.models.capital.CapitalActionType;
import com.zafra.starterapp.models.capital.CapitalResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

import static com.zafra.starterapp.models.capital.CapitalActionType.CREATE_LOAN;
import static com.zafra.starterapp.models.capital.CapitalActionType.INCREASE_LOAN;
import static com.zafra.starterapp.models.capital.CapitalActionType.PAY_LOAN;
import static com.zafra.starterapp.models.capital.CapitalActionType.TRANSACTION_PROCESSED;

public class StripeCapital {
  private Map<CapitalActionType, CapitalActionHandler> capitalActionTypeCapitalActionHandlerMap;
  private Map<String, Map<String, Book>> booksMap;

  public StripeCapital() {
    this.capitalActionTypeCapitalActionHandlerMap = Map.of(
        CREATE_LOAN, new CreateLoanHandler(),
        PAY_LOAN, new PayLoanHandler(),
        INCREASE_LOAN, new IncreaseLoanHandler(),
        TRANSACTION_PROCESSED, new TransactionActionHandler()
    );
    this.booksMap = new HashMap<>();
  }

  public List<CapitalResult> handleActions(List<String> actions) {
    actions.forEach(action -> {
      var split = action.split(":");

      var actionType = CapitalActionType.valueOf(split[0]);
      var data = parseData(split[1]);
      var handler = this.capitalActionTypeCapitalActionHandlerMap.get(actionType);

      try {
        handler.validateData(booksMap, data);
        handler.handle(booksMap, data);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    });

    List<CapitalResult> results = new ArrayList<>();

    booksMap.keySet().forEach(merchantId -> {
      var book = booksMap.get(merchantId);

      var combinedRemainingLoan = book.keySet().stream()
          .map(key -> book.get(key))
          .mapToInt(loan -> loan.getRemainingLoan())
          .sum();

      results.add(CapitalResult.builder()
          .accountId(merchantId)
          .amount(combinedRemainingLoan)
          .build());
    });

    return results;
  }

  private CapitalActionData parseData(String data) {
    var split = StringUtils.trimWhitespace(data).split(",");

    if (split.length == 3) {
      return CapitalActionData.builder()
          .merchantId(split[0])
          .loanId(split[1])
          .amount(Integer.parseInt(split[2]))
          .build();
    } else {
      return CapitalActionData.builder()
          .merchantId(split[0])
          .loanId(split[1])
          .amount(Integer.parseInt(split[2]))
          .repaymentPercentage(Integer.parseInt(split[3]))
          .build();
    }
  }
}
