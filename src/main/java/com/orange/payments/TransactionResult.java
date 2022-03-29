package com.orange.payments;

import com.orange.transactions.Transaction;

public class TransactionResult {
    private final TransactionStatus status;
    private final double transactionFee;
    private final String resultMessage;
    private final Transaction transaction;

    public TransactionResult(TransactionStatus status, double transactionFee, String resultMessage, Transaction transaction) {
        this.status = status;
        this.transactionFee = transactionFee;
        this.resultMessage = resultMessage;
        this.transaction = transaction;
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
}
