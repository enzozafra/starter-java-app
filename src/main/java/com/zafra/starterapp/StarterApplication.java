package com.zafra.starterapp;

import com.zafra.starterapp.handlers.FinnHubApiConnector;
import com.zafra.starterapp.handlers.Questrade;

//@SpringBootApplication
public class StarterApplication {

  public static void main(String[] args) throws Exception {
    FinnHubApiConnector finnHubApiConnector = new FinnHubApiConnector();
    Questrade questrade = new Questrade(finnHubApiConnector);
    questrade.handle();
  }
}
