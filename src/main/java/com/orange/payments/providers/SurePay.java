package com.orange.payments.providers;

import static com.orange.Country.india;
import static com.orange.constants.AppConstants.ORANGE_FEE;
import static com.orange.constants.AppConstants.SUREPAY_FEE_FOR_AMOUNT_GREATER_THAN_500;
import static com.orange.constants.AppConstants.SUREPAY_FEE_FOR_AMOUNT_LESS_THAN_500;
import static com.orange.constants.AppConstants.SUREPAY_FEE_FOR_HOUTPAY;

import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.PaymentProvider;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;

public class SurePay implements PaymentProvider {

  @Override
  public String getName() {

    return this.getClass().getSimpleName();
  }

  @Override
  public boolean canProcessTransaction(Transaction transaction) {
    // TODO clarify if its all transactions from India or only HoutPay transactions from India that
    // are not supported
    return !transaction.getCountry().getCountryCode().equals(india().getCountryCode());
  }

  @Override
  public double calculateTransactionFee(Transaction transaction)
      throws UnsupportedTransactionException {
    if (!canProcessTransaction(transaction)) {
      throw new UnsupportedTransactionException(
          "Card not supported for this " + this.getClass().getName());
    }
    if (transaction.getCardType().equals(CardType.HoutPay)) {
      return transaction.getTransactionAmount() * SUREPAY_FEE_FOR_HOUTPAY;
    }
    if (transaction.getTransactionAmount() <= 500) {
      return transaction.getTransactionAmount() * SUREPAY_FEE_FOR_AMOUNT_LESS_THAN_500;
    }
    return transaction.getTransactionAmount() * SUREPAY_FEE_FOR_AMOUNT_GREATER_THAN_500;
  }

  @Override
  public TransactionResult processTransaction(Transaction transaction)
      throws UnsupportedTransactionException {
    if (canProcessTransaction(transaction)) {
      double totalFee = transaction.getTransactionAmount() * ORANGE_FEE;
      double receiverWillGet =
          transaction.getTransactionAmount() - totalFee - calculateTransactionFee(transaction);
      double forwardToProvider = 0;
      forwardToProvider = calculateTransactionFee(transaction);
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
    return new TransactionResult(
        getName(), TransactionStatus.Failed, 0, "Card not supported.", transaction, 0.0, 0.0, 0.0);
  }
}
