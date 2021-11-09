package com.zafra.starterapp.handlers.server;

import com.zafra.starterapp.models.server.Log;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerRemoverTest {
  private ServerRemover serverRemover;

  @Before
  public void setup() {
    this.serverRemover = new ServerRemover();
  }

  @Test
  public void computePenalty() {
    assertThat(serverRemover.compute_penalty("0 0 1 0", 0)).isEqualTo(3);
    assertThat(serverRemover.compute_penalty("1 0 1 0", 2)).isEqualTo(3);
    assertThat(serverRemover.compute_penalty("0 0 1 0", 4)).isEqualTo(2);

    assertThat(serverRemover.compute_penalty("0 0 0 0", 10)).isEqualTo(0);
    assertThat(serverRemover.compute_penalty("1 1 1 1", 10)).isEqualTo(4);

    assertThat(serverRemover.compute_penalty("0 0 0 0", 0)).isEqualTo(4);
    assertThat(serverRemover.compute_penalty("1 1 1 1", 0)).isEqualTo(0);

    assertThat(serverRemover.compute_penalty("1", 0)).isEqualTo(0);
    assertThat(serverRemover.compute_penalty("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1", 0)).isEqualTo(0);

    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 0)).isEqualTo(2);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 1)).isEqualTo(2);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 2)).isEqualTo(1);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 3)).isEqualTo(0);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 4)).isEqualTo(1);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 5)).isEqualTo(2);
    assertThat(serverRemover.compute_penalty("0 0 1 1 1 1 1 1", 6)).isEqualTo(3);

  }

  @Test
  public void parseBoolean() {
    var expected = List.of(
        new Log(false, 1),
        new Log(false, 2),
        new Log(true, 3),
        new Log(false, 4));
    assertThat(serverRemover.parseLogs("0 0 1 0")).containsExactlyElementsOf(expected);
  }

  @Test
  public void bestTimeToRemoveServer() {
    assertThat(serverRemover.bestTimeToRemoveServer("1 1 1 0")).isEqualTo(0);
    assertThat(serverRemover.bestTimeToRemoveServer("0 0 1 1 1 1 1 1")).isEqualTo(3);

    // Test when there are 2 best times to remove, the first one is returned.
    assertThat(serverRemover.bestTimeToRemoveServer("1 0 0")).isEqualTo(0);
  }

  @Test
  public void bestTimesToRemoveServer() {
    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN END BEGIN BEGIN 1 0 0 END END 0 0 1"))
        .containsExactlyElementsOf(List.of(0));

    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN BEGIN 1 0 0 END"))
        .containsExactlyElementsOf(List.of(0));

    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN BEGIN 1 0 0 END 0 0 0 1 BEGIN 1 1 1 0 END"))
        .containsExactlyElementsOf(List.of(0, 0));

    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN END"))
        .containsExactlyElementsOf(List.of());

    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN BEGIN END END"))
        .containsExactlyElementsOf(List.of());

    assertThat(serverRemover.bestTimesToRemoveServerWithNestedLogs("BEGIN BEGIN 1 0 0 END 0 0 0 1 BEGIN 1 1 1 0 END BEGIN 0 0 1 1 1 1 1 1 END"))
        .containsExactlyElementsOf(List.of(0, 0, 3));
  }
}
