package com.zafra.starterapp.models;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class LocalePattern {
  private static final String LOCALE_PATTERN_STRING_FORMAT = "%s-%s";

  public String language;
  public String country;
  public boolean isWildCard;

  public boolean isMatch(Locale locale) {
    if (isWildCard) {
      return true;
    }

    if (this.isOnlyLanguage()) {
      return this.language.equals(locale.getLanguage());
    }

    return this.country.equals(locale.getCountry())
        && this.language.equals(locale.getLanguage());
  }

  public static LocalePattern of(String patternString) {
    var splitString = patternString.split("-");

    if (splitString.length == 2) {
      return LocalePattern.builder()
          .language(splitString[0])
          .country(splitString[1])
          .build();
    } else if (splitString.length == 1) {
      if (patternString.equals("*")) {
        return LocalePattern.builder()
            .isWildCard(true)
            .build();
      } else {
        return LocalePattern.builder()
            .language(patternString)
            .build();

      }
    } else {
      System.out.println(String.format("Pattern %s is invalid", patternString));
      return null;
    }
  }

  @Override
  public String toString() {
    return String.format(LOCALE_PATTERN_STRING_FORMAT, language, country);
  }

  private boolean isOnlyLanguage() {
    return !StringUtils.isEmpty(this.language)&& StringUtils.isEmpty(this.country);
  }
}
