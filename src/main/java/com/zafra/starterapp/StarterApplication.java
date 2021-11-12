package com.zafra.starterapp;

import com.zafra.starterapp.handlers.FinnHubApiConnector;
import com.zafra.starterapp.handlers.Questrade;

//@SpringBootApplication
public class StarterApplication {

  public static void main(String[] args) throws Exception {
    //ApplicationContext ctx = SpringApplication.run(StarterApplication.class, args);
    //
    //System.out.println("Let's inspect the beans provided by Spring Boot:");
    //
    //String[] beanNames = ctx.getBeanDefinitionNames();
    //Arrays.sort(beanNames);
    //for (String beanName : beanNames) {
    //  System.out.println(beanName);
    //}

    FinnHubApiConnector finnHubApiConnector = new FinnHubApiConnector();
    Questrade questrade = new Questrade(finnHubApiConnector);
    questrade.handle();
  }
}
