package com.zafra.starterapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterApplication {
  private static final String MEDIAN_HOUSEHOLD_INCOME_HEADER = "MEDIAN HOUSEHOLD INCOME";

  public static void main(String[] args) {
    double averageHouseholdIncome =
        // ../resources/2010.census.txt
        parseStatistics("/Users/enzo/Development/personal/starterapp/src/main/resources/2010.census.txt");
    System.out.println(String.format("The household income is: %s", averageHouseholdIncome));
  }

  public static Double parseStatistics(String filePath) {
    List<Integer> householdIncomes = new ArrayList<>();

    try {
      File myObj = new File(filePath);
      Scanner myReader = new Scanner(myObj);

      if (myReader.hasNextLine()) {
        var headers = parseColumn(myReader.nextLine());
        int householdIncomeColumnIndex = calculateIndex(headers, MEDIAN_HOUSEHOLD_INCOME_HEADER);

        while (myReader.hasNextLine()) {
          String column = myReader.nextLine();
          String[] parsedColumn = parseColumn(column);
          String householdIncomeForRow = parsedColumn[householdIncomeColumnIndex];
          Integer householdIncome = Integer.parseInt(householdIncomeForRow);

          if (householdIncome >= 0) {
            householdIncomes.add(householdIncome);
          }
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

  private static String[] parseColumn(String column) {
    return column.split("\\|");
  }

  private static int calculateIndex(String[] headers, String headerName) {
    for (int i = 0; i < headers.length; i++) {
      if (headers[i].equals(headerName)) {
        return i;
      }
    }

    return -1;
  }
}
