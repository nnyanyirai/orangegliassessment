package com.orange.payments;

import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.transactions.Transaction;

public interface PaymentProvider {
    /**
     * Retuns the name of the payment provider.
     * @return payment provider's name.
     */
    String getName();

    /**
     * Returns true if the payment processor can process the transaction.
     * @param transaction
     * @return
     */
    boolean canProcessTransaction(Transaction transaction);

    /**
     * Returns the fee that will be charged if the provider processed this transaction./
     * @param transaction
     * @return
     */
    double calculateTransactionFee(Transaction transaction) throws UnsupportedTransactionException;

    /**
     * Process the transaction and get a transaction result.
     * @param transaction
     * @return
     */
    TransactionResult processTransaction(Transaction transaction)
        throws UnsupportedTransactionException;


}
