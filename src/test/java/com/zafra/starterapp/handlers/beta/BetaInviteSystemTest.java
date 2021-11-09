package com.zafra.starterapp.handlers.beta;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BetaInviteSystemTest {
  private BetaInviteSystem betaInviteSystem;

  @Before
  public void setup() {
    betaInviteSystem = new BetaInviteSystem();
  }

  @Test
  public void handleEvents() {
    var result = betaInviteSystem.handleEvents(List.of(
        "1,invite_requested,enzo",
        "2,invite_sent,enzo",
        "3,invite_activated,enzo"
    ));

    assertThat(result.toString()).isEqualTo("0 2");
  }

  @Test
  public void handleEvents_withBot() {
    var result = betaInviteSystem.handleEvents(List.of(
        "1,invite_requested,enzo",
        "2,invite_requested,enzo",
        "3,invite_requested,enzo",
        "4,invite_requested,enzo",
        "5,invite_requested,enzo"
    ));

    assertThat(result.toString()).isEqualTo("1 -1");
  }

  @Test
  public void handleEvents_with_many_emails() {
    var result = betaInviteSystem.handleEvents(List.of(
        "1,invite_requested,enzo",
        "2,invite_requested,enzo",
        "3,invite_requested,enzo",
        "4,invite_requested,enzo",
        "5,invite_requested,enzo",

        "61,invite_requested,kyle",
        "62,invite_requested,kyle",
        "63,invite_requested,kyle",
        "64,invite_requested,kyle",
        "65,invite_requested,kyle",

        "1000,invite_requested,mike",
        "1023,invite_sent,mike",
        "2000,invite_activated,mike",

        "3000,invite_requested,keem",
        "3001,invite_sent,keem",
        "5000,invite_activated,keem"
    ));

    assertThat(result.toString()).isEqualTo("2 1500");
  }
}
