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
public class HandlerTest {
  @Mock private DependedHandler dependedHandler;

  private Handler handler;

  @Before
  public void setup() {
    handler = new Handler(dependedHandler);
  }

  @Test
  public void handle_returnsMessage() {
    assertThat(handler.handle()).isEqualTo("Hi im the handler");
  }

  @Test
  public void dependedHandlerMessage_delegatesDependedHandler() {
    handler.dependedHandlerMessage();
    verify(dependedHandler).handle();
  }

  @Test
  public void dependedHandlerMessage_mockedMessage() {
    when(dependedHandler.handle()).thenReturn("Hi im mocked");
    assertThat(handler.dependedHandlerMessage()).isEqualTo("Hi im mocked");
  }
}
