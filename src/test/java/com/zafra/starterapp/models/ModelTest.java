package com.zafra.starterapp.models;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ModelTest {

  @Before
  public void setup() {
    // Do nothing for now
  }

  @Test
  public void getId_happyPath() {
    var mockedId = "some_id";
    var expectedModel = Model.builder()
        .id(mockedId)
        .name("some_name")
        .owner(Optional.of("some_owner"))
        .build();

    assertThat(expectedModel.getId()).isEqualTo(mockedId);
  }
}
