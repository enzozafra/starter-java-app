package com.zafra.starterapp.models;

import java.util.Map;
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

  @Test
  public void matchingWithCountryWildcard() {
    var localePatternToTest = LocalePattern.of("en-%S");
    assertThat(localePatternToTest.isCountryEquals("US")).isTrue();
    assertThat(localePatternToTest.isCountryEquals("SS")).isTrue();
    assertThat(localePatternToTest.isCountryEquals("S")).isFalse();
    assertThat(localePatternToTest.isCountryEquals("FR")).isFalse();

    var localePatternToTestStar = LocalePattern.of("en-*");
    assertThat(localePatternToTestStar.isCountryEquals("US")).isTrue();
    assertThat(localePatternToTestStar.isCountryEquals("SS")).isTrue();
    assertThat(localePatternToTestStar.isCountryEquals("S")).isTrue();
    assertThat(localePatternToTestStar.isCountryEquals("FR")).isTrue();
  }

  @Test
  public void matchingWithLanguageWildcard() {
    var localePatternToTest = LocalePattern.of("e*-US");
    assertThat(localePatternToTest.isLanguageEquals("en")).isTrue();
    assertThat(localePatternToTest.isLanguageEquals("es")).isTrue();
    assertThat(localePatternToTest.isLanguageEquals("n")).isFalse();
    assertThat(localePatternToTest.isLanguageEquals("Unn")).isFalse();

    var localePatternToTestStar = LocalePattern.of("*-US");
    assertThat(localePatternToTestStar.isLanguageEquals("en")).isTrue();
    assertThat(localePatternToTestStar.isLanguageEquals("e")).isTrue();
    assertThat(localePatternToTestStar.isLanguageEquals("es")).isTrue();
  }
}
