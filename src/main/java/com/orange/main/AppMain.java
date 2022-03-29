package com.orange.main;

import com.orange.payments.PaymentProcessor;
import com.orange.payments.TransactionResult;
import com.orange.transactions.Transaction;
import com.orange.transactions.Transactions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppMain {
  public static void main(String[] args) {
    double transactionTotals = 0;
    Transactions transactions = new Transactions();
    PaymentProcessor processor = new PaymentProcessor();
    List<TransactionResult> transactionResults = new ArrayList<>();
    for (Transaction transaction : transactions.getTransactions()) {
      TransactionResult transactionResult = processor.processPayment(transaction);
      transactionResults.add(transactionResult);
      transactionTotals = transactionTotals + transaction.getTransactionAmount();
    }

    Map<String, List<TransactionResult>> transactionResultsByProvider =
        transactionResults.stream()
            .collect(Collectors.groupingBy(TransactionResult::getProviderName));

    Map<String, Double> providerTotals =
        transactionResults.stream()
            .collect(
                Collectors.groupingBy(
                    TransactionResult::getProviderName,
                    Collectors.summingDouble(TransactionResult::getForwardToProvider)));

    Map<String, Double> providerTotalCharges =
        transactionResults.stream()
            .collect(
                Collectors.groupingBy(
                    TransactionResult::getProviderName,
                    Collectors.summingDouble(TransactionResult::getProviderCharge)));

    Map<String, Long> transCountPerProvider =
        transactionResults.stream()
            .collect(
                Collectors.groupingBy(TransactionResult::getProviderName, Collectors.counting()));

    Double orangeRevenue =
        transactionResults.stream()
            .collect(Collectors.summingDouble(TransactionResult::getRevenue));

    Double totalPaidToProviders =
        transactionResults.stream()
            .collect(Collectors.summingDouble(TransactionResult::getProviderCharge));

    System.out.println(
        "******************** Printing Providers that process with least charge *****************************");
    for (Map.Entry<String, List<TransactionResult>> e : transactionResultsByProvider.entrySet()) {
      for (TransactionResult e1 : e.getValue())
        System.out.println(
            "Provider Chosen for this transaction is "
                + e.getKey()
                + " -- provider charge is  =  "
                + e1.getProviderCharge());
    }
    System.out.println(
        "******************** Printing Total Paid to Providers *****************************");
    System.out.println("Total Paid to Providers is = " + totalPaidToProviders);

    System.out.println(
        "******************** Printing Total Charges per provider *****************************");
    for (Map.Entry<String, Double> e : providerTotalCharges.entrySet()) {
      System.out.println(
          "Provider is " + e.getKey() + " -- Total Charges =  " + e.getValue());
    }

    System.out.println(
        "******************** Printing number of Transactions per Provider *****************************");
    for (Map.Entry<String, Long> e : transCountPerProvider.entrySet()) {
      System.out.println(
          "Provider is " + e.getKey() + " -- considered for  =  " + e.getValue() + " times.");
    }

    System.out.println(
        "******************** Printing Orange Revenue *****************************");
    System.out.println("Orange Revenue is " + orangeRevenue);

    System.out.println(
        "******************** Printing Total value of all transactions processed by Orange ***********************");
    System.out.println("Total transactions value is " + transactionTotals);

  }
}
