package com.zafra.starterapp.handlers.questrade;

import com.zafra.starterapp.models.questrade.Quote;
import com.zafra.starterapp.models.questrade.StockSymbol;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Questrade {
  private FinnHubApiConnector finnHubApiConnector;

  public Questrade(FinnHubApiConnector finnHubApiConnector) {
    this.finnHubApiConnector = finnHubApiConnector;
  }

  public void handle() throws Exception {
    List<StockSymbol> stockSymbolList = this.finnHubApiConnector.findSymbol("apple");
    stockSymbolList.stream()
        .map(StockSymbol::toString)
        .forEach(System.out::println);

    List<Quote> quotes = stockSymbolList.stream()
        .map(stockSymbol -> this.finnHubApiConnector.getQuote(stockSymbol.getSymbol()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toUnmodifiableList());

    quotes.stream()
        .map(Quote::toString)
        .forEach(System.out::println);
  }
}
