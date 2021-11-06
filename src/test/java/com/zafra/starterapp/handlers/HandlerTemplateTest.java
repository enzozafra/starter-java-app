package com.zafra.starterapp.handlers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HandlerTemplateTest {

  private HandlerTemplate handlerTemplate;

  @Before
  public void setup() {
    handlerTemplate = new HandlerTemplate();
  }

  @Test
  public void handle_successful() {
    //assertThat(handler.handle()).isEqualTo("Hi im the handler");
  }
}
