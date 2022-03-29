package com.orange.payments.providers;

import static com.orange.Country.southAfrica;
import static com.orange.constants.AppConstants.FEE_FOR_HOUT_PAYCARD_IN_SA;
import static com.orange.constants.AppConstants.FEE_FOR_OTHER_CARDS;
import static com.orange.constants.AppConstants.ORANGE_FEE;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.PaymentProvider;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//  This code is an example and is incorrect.
public class HoutPay implements PaymentProvider {

private double totalFee = 0;
@Override
public String getName() {

    return this.getClass().getSimpleName();
    }

@Override
public boolean canProcessTransaction(Transaction transaction) {
    if (transaction.getCardType().equals(CardType.HoutPay) && !transaction.getCountry()
    .getCountryCode().equalsIgnoreCase(southAfrica().getCountryCode())) {
    return false;
    }
    return true;
    }

@Override
public double calculateTransactionFee(Transaction transaction) throws UnsupportedTransactionException {
    if (canProcessTransaction(transaction)) {
    return transaction.getTransactionAmount() * determineApplicablePercentage(transaction);
    }
    throw new UnsupportedTransactionException("Card not supported for this " + this.getClass().getName());
    }

private double determineApplicablePercentage(Transaction transaction) {
    if (transaction.getCardType().equals(CardType.HoutPay)) {
    return FEE_FOR_HOUT_PAYCARD_IN_SA;
    }
    return FEE_FOR_OTHER_CARDS;
    }

@Override
public TransactionResult processTransaction(Transaction transaction) throws UnsupportedTransactionException{
    if (canProcessTransaction(transaction)) {
    totalFee = transaction.getTransactionAmount() * ORANGE_FEE;
    double receiverWillGet = transaction.getTransactionAmount() - totalFee;
    double forwardToProvider = 0;
    forwardToProvider = receiverWillGet / (1 - calculateTransactionFee(transaction));
    return new TransactionResult(
    getName(),
    TransactionStatus.Success,
    calculateTransactionFee(transaction),
    "Transaction successful.",
    transaction,
    totalFee,
    receiverWillGet,
    forwardToProvider);
    }
    return new TransactionResult(getName(), TransactionStatus.Failed, 0, "Card not supported.",
    transaction, totalFee, 0.0, 0.0);
    }
}
