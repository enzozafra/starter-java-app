package com.zafra.starterapp.models;

import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Locale {
  private static final String LOCALE_STRING_FORMAT = "%s-%s";
  public String language;
  public String country;

  public static Locale of(String localeString) {
    var splitString = localeString.split("-");
    //
    //if (splitString.length != 2) {
    //  throw new Exception(String.format("Locale string %s is invalid", localeString));
    //}

    return Locale.builder()
        .language(splitString[0])
        .country(splitString[1])
        .build();
  }

  @Override
  public String toString() {
    return String.format(LOCALE_STRING_FORMAT, language, country);
  }
}
