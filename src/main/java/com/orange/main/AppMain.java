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
        Transactions transactions = new Transactions();
        PaymentProcessor processor = new PaymentProcessor();
        List<TransactionResult> transactionResults = new ArrayList<>();
        for (Transaction transaction : transactions.getTransactions()) {
            TransactionResult transactionResult = processor.processPayment(transaction);
            transactionResults.add(transactionResult);
        }

        Map<String, List<TransactionResult>> transactionResultsByProvider = transactionResults.stream()
            .collect(Collectors.groupingBy(TransactionResult::getProviderName));
        Map<String, Double> providerTotals = transactionResults.stream()
            .collect(Collectors.groupingBy(TransactionResult::getProviderName,
                Collectors.summingDouble(TransactionResult::getForwardToProvider)));
        Map<String, Double> providerTotalCharges = transactionResults.stream()
            .collect(Collectors.groupingBy(TransactionResult::getProviderName,
                Collectors.summingDouble(TransactionResult::getProviderCharge)));
        Double orangeRevenue = transactionResults.stream()
            .collect(Collectors.summingDouble(TransactionResult::getRevenue));
        System.out.println("******************** Printing Providers that process with least charge *****************************");
        for(Map.Entry<String, List<TransactionResult>> e : transactionResultsByProvider.entrySet()){
            for(TransactionResult e1 : e.getValue())
                System.out.println("Provider Chosen for this transaction is " + e.getKey() + " -- provider charge is  =  "+ e1.getProviderCharge());
        }


        System.out.println("******************** Printing Providers Total Charges *****************************");
        for(Map.Entry<String, Double> e : providerTotalCharges.entrySet()){
            System.out.println("Provider is " + e.getKey() + " -- Total Charges for Provider  =  "+ e.getValue());
        }

        System.out.println("******************** Printing Providers Totals *****************************");
        for(Map.Entry<String, Double> e : providerTotals.entrySet()){
            System.out.println("Provider is " + e.getKey() + " -- Total charge for Provider  =  "+ e.getValue());
        }


        System.out.println("******************** Printing Orange Revenue *****************************");
        System.out.println("Orange Revenue is " + orangeRevenue);
    }
}