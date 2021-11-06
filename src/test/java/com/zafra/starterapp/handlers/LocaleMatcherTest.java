package com.zafra.starterapp.handlers;

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
    var locales = List.of("en-CA", "en-FR", "en-US");
    var patterns = List.of("xy-YZ", "ab-CD", "en-US");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("en-US");
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_noMatch() {
    var locales = List.of("en-CA", "en-FR", "en-US");
    var patterns = List.of("xy-YZ", "ab-CD", "en-PL");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of();
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_noPatterns() {
    var locales = List.of("en-CA", "en-FR", "en-US");
    List<String> patterns = List.of();

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of();
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_noLocales() {
    List<String> locales = List.of();
    var patterns = List.of("xy-YZ", "ab-CD", "en-PL");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of();
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_genericLanguage() {
    var locales = List.of("en-CA", "en-FR", "en-US");
    var patterns = List.of("xy-YZ", "ab-CD", "en");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("en-CA", "en-FR", "en-US");
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void getValidLocales_genericCountry() {
    var locales = List.of("en-CA", "en-FR", "en-US", "es-FR");
    var patterns = List.of("xy-YZ", "ab-CD", "FR");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("es-FR", "en-FR");
    assertThat(actualResult).hasSameElementsAs(expectedResult);
  }

  @Test
  public void getValidLocales_withLanguageWildCards() {
    var locales = List.of("en-CA", "es-CA", "ss-CA", "es-US", "en-US", "es-FR");
    var patterns = List.of("e%-CA", "*-US");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("en-CA", "es-CA", "es-US", "en-US");
    assertThat(actualResult).hasSameElementsAs(expectedResult);
  }

  @Test
  public void getValidLocales_withCountryWildCards() {
    var locales = List.of("en-CA", "en-US", "en-SP", "es-CA", "es-CC", "es-AB");
    var patterns = List.of("en-*", "es-C%");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("en-CA", "en-US", "en-SP", "es-CA", "es-CC");
    assertThat(actualResult).hasSameElementsAs(expectedResult);
  }

  @Test
  public void getValidLocales_genericWithWildcard() {
    var locales = List.of("en-CA", "es-US", "en-SP", "fr-CA", "fr-CC", "fr-US");
    var patterns = List.of("e%", "U%");

    var actualResult = localeMatcher.getValidLocales(locales, patterns);

    var expectedResult = List.of("en-CA", "es-US", "en-SP", "fr-US");
    assertThat(actualResult).hasSameElementsAs(expectedResult);
  }
}
