package com.orange.payments;

import com.orange.payments.providers.HoutPay;
import com.orange.payments.providers.QuickPay;
import com.orange.payments.providers.SurePay;
import com.orange.transactions.Transaction;

import java.util.Arrays;
import java.util.List;

public class PaymentProcessor {
    private List<PaymentProvider> providers;

    public PaymentProcessor() {
        providers = Arrays.asList(
                new HoutPay(),
                new SurePay(),
                new QuickPay(),
                new PayMasters()
        );
    }

    public TransactionResult processPayment(Transaction transaction) {
        return null;
    }
}
