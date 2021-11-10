package com.zafra.starterapp.handlers.locale;

import com.zafra.starterapp.models.locale.Locale;
import com.zafra.starterapp.models.locale.LocalePattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;

import static com.google.common.collect.ImmutableList.toImmutableList;

/**
 * 1. Given a list of languages in a header and a list of languages a server accepts,
 * return the languages that will be processed in the same order sent in the header.
 * ex. header = "en-CA, en-FR, en-US", accepted = ["xy-YZ", "ab-CD", "en-US"]
 *  => ["en-US"]
 *
 * 2. Assume the header now the server now accepts some languages generically.
 * ex. header = "en", accepted = ["en-US", "fr-CA", "fr-FR", "en-CA"]
 * => ["en-US", "en-CA"]
 *
 * 3. Assume the server can accept a wildcard.
 * ex. header = "en-US, *", accepted = ["en-US", "fr-CA", "fr-FR"]
 * => ["en-US", "fr-CA", "fr-FR"]
 *
 * ex. header = "fr-FR, fr, *", accepted = ["en-US", "fr-CA", "fr-FR"]
 * => ["fr-FR", "fr-CA", "en-US"]
 */
public class LocaleMatcher {

  public LocaleMatcher() { }

  public List<String> getValidLocales(String localeHeaders, List<String> validLocales) {
    var parsedLocales = parseLocales(validLocales);
    var parsedPatterns = parsePatterns(localeHeaders);

    return getValidLocales(parsedLocales, parsedPatterns)
        .stream()
        .map(Locale::toString)
        .collect(toImmutableList());
  }

  private List<LocalePattern> parsePatterns(String locales) {
    return Arrays.stream(locales.split(","))
        .map(token -> StringUtils.trimAllWhitespace(token))
        .map(stringLocale -> LocalePattern.of(stringLocale))
        .collect(toImmutableList());
  }

  private List<Locale> parseLocales(List<String> stringPatterns) {
    return stringPatterns
        .stream()
        .map(stringPattern -> Locale.of(stringPattern))
        .collect(toImmutableList());
  }

  private List<Locale> getValidLocales(List<Locale> locales, List<LocalePattern> patterns) {
    Set<Locale> accepted = new HashSet<>();
    List<Locale> orderedAcceptedLocales = new ArrayList<>();

    patterns.forEach(localePattern -> {
      locales.forEach(locale -> {
        if (localePattern.isMatch(locale) && !accepted.contains(locale)) {
          orderedAcceptedLocales.add(locale);
          accepted.add(locale);
        }
      });
    });

    return orderedAcceptedLocales;
  }
}
