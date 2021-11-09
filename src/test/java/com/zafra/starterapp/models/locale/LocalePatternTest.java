package com.zafra.starterapp.models.locale;

import com.zafra.starterapp.models.locale.LocalePattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocalePatternTest {

  @Test
  public void parsing_successful() {
    // Parse string then convert back to string to test a full loop of conversion
    var localeToTest = "en-US";
    LocalePattern parsedLocale = LocalePattern.of(localeToTest);
    String stringLocale = parsedLocale.toString();

    assertThat(stringLocale).isEqualTo(localeToTest);
  }
}
