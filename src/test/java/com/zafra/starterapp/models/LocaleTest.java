package com.zafra.starterapp.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocaleTest {

  @Test
  public void parsing_successful() throws Exception {
    // Parse string then convert back to string to test a full loop of conversion
    var localeToTest = "en-US";
    Locale parsedLocale = Locale.of(localeToTest);
    String stringLocale = parsedLocale.toString();

    assertThat(stringLocale).isEqualTo(localeToTest);
  }

  //@Test
  //public void parsing_exception() throws Exception {
  //  var localeToTest = "US";
  //
  //  assertThatThrownBy(() -> Locale.of(localeToTest))
  //      .isInstanceOf(Exception.class).hasMessageContaining("Locale string US is invalid");
  //}
}
