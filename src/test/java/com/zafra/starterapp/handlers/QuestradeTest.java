package com.zafra.starterapp.handlers;

import com.zafra.starterapp.handlers.questrade.FinnHubApiConnector;
import com.zafra.starterapp.handlers.questrade.Questrade;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuestradeTest {
  @Mock private FinnHubApiConnector finnHubApiConnector;

  private Questrade questrade;

  @Before
  public void setup() {
    questrade = new Questrade(finnHubApiConnector);
  }

  //@Test
  //public void handle_returnsMessage() throws Exception {
  //}
}
