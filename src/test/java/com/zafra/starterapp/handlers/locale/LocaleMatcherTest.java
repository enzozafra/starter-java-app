package com.zafra.starterapp.handlers.locale;

import com.zafra.starterapp.handlers.locale.LocaleMatcher;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocaleMatcherTest {

  private LocaleMatcher localeMatcher;

  @Before
  public void setup() {
    localeMatcher = new LocaleMatcher();
  }

  @Test
  public void getValidLocales_happyPath() {
    var patterns = "en-CA, en-FR, en-US";
    var locales = List.of("xy-YZ", "ab-CD", "en-US");

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of("en-US");
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_noMatch() {
    var patterns = "en-CA, en-FR, en-US";
    var locales = List.of("xy-YZ", "ab-CD", "en-PL");

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of();
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_noPatterns() {
    var patterns = "en-CA, en-FR, en-US";
    List<String> locales = List.of();

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of();
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_genericLanguage() {
    var patterns = "en";
    List<String> locales = List.of("en-US", "fr-CA", "fr-FR", "en-CA");

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of("en-US", "en-CA");
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_wildCard() {
    var patterns = "en-US, *";
    List<String> locales = List.of("en-US", "fr-CA", "fr-FR");

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of("en-US", "fr-CA", "fr-FR");
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_wildCardMaintainsSortedOrder() {
    var patterns = "fr-FR, fr, *";
    List<String> locales = List.of("en-US", "fr-CA", "fr-FR");

    var actualResult = localeMatcher.getValidLocales(patterns, locales);

    var expectedResult = List.of("fr-FR", "fr-CA", "en-US");
    assertThat(actualResult).isEqualTo(expectedResult);
  }
}
