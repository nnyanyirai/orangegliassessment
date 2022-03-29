package com.orange.main;

import com.orange.payments.PaymentProcessor;
import com.orange.transactions.Transaction;
import com.orange.transactions.Transactions;

public class AppMain {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        for (Transaction transaction : new Transactions().getTransactions()) {


        }
    }
}
