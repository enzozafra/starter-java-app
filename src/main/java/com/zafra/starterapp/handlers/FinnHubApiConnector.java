package com.zafra.starterapp.handlers;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zafra.starterapp.models.FindSymbolResponse;
import com.zafra.starterapp.models.Quote;
import com.zafra.starterapp.models.StockSymbol;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FinnHubApiConnector {
  private static final String HOST = "finnhub.io";
  private static final String SEARCH_ENDPOINT = "search";
  private static final String QUOTE_ENDPOINT = "quote";

  private static final String API_KEY_HEADER = "X-Finnhub-Token";
  private static final String API_KEY = "c67c2u2ad3iai8ras9ag";

  private final OkHttpClient client;
  private final Gson gson;

  public FinnHubApiConnector() {
    this.client = new OkHttpClient();
    this.gson = new Gson();
  }

  public List<StockSymbol> findSymbol(String companyName) {
    HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder();
    httpUrlBuilder.addPathSegment(SEARCH_ENDPOINT);
    httpUrlBuilder.addQueryParameter("q", companyName);

    Request request = new Request.Builder()
        .url(httpUrlBuilder.build())
        .addHeader(API_KEY_HEADER, API_KEY)
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
      FindSymbolResponse stockSymbols =
          gson.fromJson(response.body().string(), FindSymbolResponse.class);
      return stockSymbols.getResult();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return List.of();
  }

  public Optional<Quote> getQuote(String symbol) {
    HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder();
    httpUrlBuilder.addPathSegment(QUOTE_ENDPOINT);
    httpUrlBuilder.addQueryParameter("symbol", symbol);

    Request request = new Request.Builder()
        .url(httpUrlBuilder.build())
        .addHeader(API_KEY_HEADER, API_KEY)
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
      Quote quote =
          gson.fromJson(response.body().string(), Quote.class);
      return Optional.of(quote);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return Optional.empty();
  }

  private HttpUrl.Builder getHttpUrlBuilder() {
    return new HttpUrl.Builder()
        .scheme("https")
        .host(HOST)
        .addPathSegment("api")
        .addPathSegment("v1");
  }
}
