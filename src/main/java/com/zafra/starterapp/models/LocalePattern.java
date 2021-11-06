package com.zafra.starterapp.models;

import com.google.common.annotations.VisibleForTesting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalePattern {
  private static final String LOCALE_PATTERN_STRING_FORMAT = "%s-%s";

  public String language;
  public String country;
  public boolean hasWildCard;

  public boolean isMatch(Locale locale) {
    if (!this.hasWildCard) {
      return this.isLanguageEquals(locale.getLanguage())
          && this.isCountryEquals(locale.getCountry());
    }

    return this.language == null
        ? this.isCountryEquals(locale.getCountry())
        : this.isLanguageEquals(locale.getLanguage());
  }

  public static LocalePattern of(String patternString) {
    var splitString = patternString.split("-");

    if (splitString.length == 2) {
      return LocalePattern.builder()
          .language(splitString[0])
          .country(splitString[1])
          .hasWildCard(false)
          .build();
    } else if (splitString.length == 1) {
      if (isUpperCase(patternString
          .replace("%", "")
          .replace("*", ""))) {
        return LocalePattern.builder()
            .country(patternString)
            .hasWildCard(true)
            .build();
      } else {
        return LocalePattern.builder()
            .language(patternString)
            .hasWildCard(true)
            .build();

      }
    } else {
      System.out.println(String.format("Pattern %s is invalid", patternString));
      return null;
    }
  }

  public static boolean isUpperCase(String s)
  {
    for (int i=0; i<s.length(); i++) {
      if (!Character.isUpperCase(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format(LOCALE_PATTERN_STRING_FORMAT, language, country);
  }

  @VisibleForTesting boolean isLanguageEquals(String language) {
    var replacedWildCard = this.language
        .replace("*", "\\w*")
        .replace("%", "\\w");

    Pattern p = Pattern.compile(String.format("^%s$", replacedWildCard));

    Matcher m = p.matcher(language);
    return m.matches();
  }

  @VisibleForTesting boolean isCountryEquals(String country) {
    var replacedWildCard = this.country
        .replace("*", "\\w*")
        .replace("%", "\\w");

    Pattern p = Pattern.compile(String.format("^%s$", replacedWildCard));

    Matcher m = p.matcher(country);
    return m.matches();
  }
}
