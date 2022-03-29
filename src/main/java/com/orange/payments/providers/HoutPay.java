package com.orange.payments.providers;

import com.orange.payments.PaymentProvider;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
//  This code is an example and is incorrect.
public class HoutPay implements PaymentProvider {
    private static final String name = "HoutPay";
    private static final double fee = 0.03;
    private double totalTransactionValue = 0;
    private double totalFee = 0;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean canProcessTransaction(Transaction transaction) {
        return transaction.getCardType().equals(CardType.HoutPay);
    }

    @Override
    public double calculateTransactionFee(Transaction transaction) {
        return transaction.getTransactionAmount() * fee;
    }

    @Override
    public TransactionResult processTransaction(Transaction transaction) {
        // The below code is an example.  It is not correct though.
        if (transaction.getCardType().equals(CardType.HoutPay)) {
            //todo - Add code to accumelate transaction totals.

            return new TransactionResult(
                    TransactionStatus.Success,
                    calculateTransactionFee(transaction), "Transaction successful.", transaction);
        } else {
            return new TransactionResult(TransactionStatus.Failed, 0, "Card not supported.", transaction);
        }
    }
}
