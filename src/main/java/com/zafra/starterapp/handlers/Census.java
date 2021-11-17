package com.zafra.starterapp.handlers;

//The file '2010.census.txt' contains summary statistics from the 2010 United
//    States census including household income. The data is in an unspecified
//    format.
//
//    Find the average of the column called:
//
//    'MEDIAN HOUSEHOLD INCOME'
//
//    Ideally the solution should be a command line script, of the form:
//
//    $ ./solution [options] [file...]
//
//    The solution may be written in any language, Python is preferred but not
//    required.
//
//    Google, stack overflow, etc. usage is allowed.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//
//CENSUS YEAR|TRACT|BLOCK GROUP|FIPS ID|TOTAL POPULATION|POPULATION WHITE|POPULATION BLACK|POPULATION ASIAN|POPULATION OTHER|POPULATION AMERICAN INDIAN|POPULATION PACIFIC ISLANDER|POPULATION ONE RACE|POPULATION MULTI RACE|POPULATION 25 OLDER|MEDIAN AGE|MEDIAN HOUSEHOLD INCOME|HIGH SCHOOL MALE|HIGH SCHOOL MORE MALE|COLLEGE 1 YR LESS MALE|COLLEGE 1 YR MORE MALE|ASSOCIATES DEGREE MALE|BACHELORS DEGREE MALE|MASTERS DEGREE MALE|PROFESSIONAL DEGREE MALE|DOCTORAL DEGREE MALE|HIGH SCHOOL FEMALE|HIGH SCHOOL MORE FEMALE|COLLEGE 1 YR LESS FEMALE|COLLEGE 1 YR MORE FEMALE|ASSOCIATES DEGREE FEMALE|BACHELORS DEGREE FEMALE|MASTERS DEGREE FEMALE|PROFESSIONAL DEGREE FEMALE|DOCTORAL DEGREE FEMALE|PERCENT 25 YR OVER HIGH SCHOOL MORE|HOUSING UNITS|OCCUPIED HOUSING UNITS|OWNER OCCUPIED HOUSING|RENTER OCCUPIED HOUSING|PERCENT OWNER OCCUPIED|PERCENT RENTER OCCUPIED|MEDIAN HOUSE VALUE OWNER OCCUPIED|MEDIAN YEAR BUILT|VACANCY RATES
//    2010|1|1|220330001001|1624|73|1529|3|3|0|0|1608|16|893|28.4|28300|170|101|10|44|7|40|0|0|0|107|324|22|171|78|37|16|0|0|43.38%|585|498|669|1111|37.58%|62.42%|$67800.00|1969|14.87%
//    2010|2|1|220330002001|1171|112|1039|0|11|0|0|1162|9|683|31.7|32810|118|31|0|18|0|0|13|0|0|42|112|0|0|0|99|13|0|0|28.35%|550|439|518|701|42.49%|57.51%|$58300.00|1966|20.18%
//    2010|2|2|220330002002|1284|63|1209|5|0|1|0|1278|6|632|24.4|30641|128|26|0|26|0|0|0|0|0|343|143|50|75|0|18|0|0|0|16.29%|534|511|841|742|53.13%|46.87%|$103300.00|1960|4.31%
public class Census {
  // Map:
  // CENSUS YEAR: 0
  // MEDIAN HOUSEHOLD INCOME':

  //try {
  //  File myObj = new File("filename.txt");
  //  Scanner myReader = new Scanner(myObj);
  //  while (myReader.hasNextLine()) {
  //    String data = myReader.nextLine();
  //    System.out.println(data);
  //  }
  //  myReader.close();
  //} catch (FileNotFoundException e) {
  //  System.out.println("An error occurred.");
  //  e.printStackTrace();
  //}

  private Map<String, Integer> headerToColumnIndexMap;

  public Census() {
    this.headerToColumnIndexMap = new HashMap<>();
  }

  public Double parseStatistics() {
    List<Integer> householdIncomes = new ArrayList<>();

    try {
      File myObj = new File("2010.census.txt");
      Scanner myReader = new Scanner(myObj);

      if (myReader.hasNextLine()) {
        var headers = parseColumn(myReader.nextLine());
        int householdIncomeColumnIndex = calculateIndex(headers, "MEDIAN HOUSEHOLD INCOME");

        while (myReader.hasNextLine()) {
          String column = myReader.nextLine();
          String[] parsedColumn = parseColumn(column);
          Integer householdIncomeString =
              Integer.parseInt(parsedColumn[householdIncomeColumnIndex]);
          householdIncomes.add(householdIncomeString);
        }
      }

      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    return householdIncomes.stream()
        .mapToInt(item -> item)
        .average()
        .getAsDouble();
  }

  private String[] parseColumn(String column) {
    return column.split("|");
  }

  private int calculateIndex(String[] headers, String headerName) {
    for (int i = 0; i < headers.length; i++) {
      if (headers[i].equals(headerName)) {
        return i;
      }
    }

    return -1;
  }
}
