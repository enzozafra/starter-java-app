package com.zafra.starterapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
  private String company;
  private double amount;
  private int timestamp;
}
