package com.orange.payments;

import com.orange.transactions.Transaction;

public class TransactionResult {
  private final String providerName;
  private final TransactionStatus status;
  private final double transactionFee;
  private final String resultMessage;
  private final Transaction transaction;
  private final double totalFee;
  private final double receiverWillGet;
  private final double forwardToProvider;

  public TransactionResult(
      String providerName,
      TransactionStatus status,
      double transactionFee,
      String resultMessage,
      Transaction transaction,
      double totalFee,
      double receiverWillGet,
      double forwardToProvider) {
    this.providerName = providerName;
    this.status = status;
    this.transactionFee = transactionFee;
    this.resultMessage = resultMessage;
    this.transaction = transaction;
    this.totalFee = totalFee;
    this.receiverWillGet = receiverWillGet;
    this.forwardToProvider = forwardToProvider;
  }

  public String getProviderName() {
    return providerName;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public double getTransactionFee() {
    return transactionFee;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public double getTotalFee() {
    return totalFee;
  }

  public double getReceiverWillGet() {
    return receiverWillGet;
  }

  public double getForwardToProvider() {
    return forwardToProvider;
  }

  public double getProviderCharge() {

    return (forwardToProvider);
  }

  public double getRevenue() {
    return totalFee - getProviderCharge();
  }

  @Override
  public String toString() {
    return "TransactionResult{"
        + "providerName='"
        + providerName
        + '\''
        + ", status="
        + status
        + ", transactionFee="
        + transactionFee
        + ", resultMessage='"
        + resultMessage
        + '\''
        + ", transaction="
        + transaction
        + ", totalFee="
        + totalFee
        + ", receiverWillGet="
        + receiverWillGet
        + ", forwardToProvider="
        + forwardToProvider
        + '}';
  }
}
