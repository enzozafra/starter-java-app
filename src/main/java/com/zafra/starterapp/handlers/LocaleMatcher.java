package com.zafra.starterapp.handlers;

import com.zafra.starterapp.models.Locale;
import com.zafra.starterapp.models.LocalePattern;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class LocaleMatcher {

  public LocaleMatcher() { }

  public List<String> getValidLocales(List<String> locales, List<String> validLocales) {
    var parsedLocales = parseLocales(locales);
    var parsedPatterns = parsePatterns(validLocales);

    return getValidLocalesHelper(parsedLocales, parsedPatterns)
        .stream()
        .map(Locale::toString)
        .collect(toImmutableList());
  }

  private List<Locale> parseLocales(List<String> stringLocales) {
    return stringLocales
        .stream()
        .map(stringLocale -> Locale.of(stringLocale))
        .collect(toImmutableList());
  }

  private List<LocalePattern> parsePatterns(List<String> stringPatterns) {
    return stringPatterns
        .stream()
        .map(stringPattern -> LocalePattern.of(stringPattern))
        .collect(toImmutableList());
  }

  private List<Locale> getValidLocalesHelper(List<Locale> locales, List<LocalePattern> validLocales) {
    List<Locale> acceptedLocales = new ArrayList<>();

    locales.forEach(locale -> {
      validLocales.forEach(localePattern -> {
        if (localePattern.isMatch(locale)) {
          acceptedLocales.add(locale);
        }
      });
    });

    return acceptedLocales;
  }

}
