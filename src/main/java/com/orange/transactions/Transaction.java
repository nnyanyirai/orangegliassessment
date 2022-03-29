package com.orange.transactions;

import com.orange.Country;

public class Transaction {

  private final long customerId;
  private final CardType cardType;
  private final Country country;
  private int transactionAmount;

  @Override
  public String toString() {
    return "Transaction{"
        + "customerId="
        + customerId
        + ", cardType="
        + cardType
        + ", country="
        + country
        + ", transactionAmount="
        + transactionAmount
        + '}';
  }

  public Transaction(long customerId, CardType cardType, Country country, int transactionAmount) {
    this.customerId = customerId;
    this.cardType = cardType;
    this.country = country;
    this.transactionAmount = transactionAmount;
  }

  public long getCustomerId() {
    return customerId;
  }

  public CardType getCardType() {
    return cardType;
  }

  public Country getCountry() {
    return country;
  }

  public int getTransactionAmount() {
    return transactionAmount;
  }
}
