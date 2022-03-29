package com.orange.payments.providers;

import static com.orange.Country.india;
import static com.orange.Country.southAfrica;
import static com.orange.constants.AppConstants.ORANGE_FEE;
import static com.orange.constants.AppConstants.QUICKPAY_FEE_FOR_ALL_OTHER_CARDS;
import static com.orange.constants.AppConstants.QUICKPAY_FEE_FOR_DINNERS_FROM_INDIA;
import static com.orange.constants.AppConstants.QUICKPAY_FEE_FOR_DINNERS_NOT_FROM_INDIA;

import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.PaymentProvider;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;

public class QuickPay implements PaymentProvider {

  private double totalFee = 0;

  @Override
  public String getName() {

    return this.getClass().getSimpleName();
  }

  @Override
  public boolean canProcessTransaction(Transaction transaction) {
    if (transaction
        .getCountry()
        .getCountryCode()
        .equalsIgnoreCase(southAfrica().getCountryCode())) {
      return false;
    }
    return true;
  }

  @Override
  public double calculateTransactionFee(Transaction transaction)
      throws UnsupportedTransactionException {
    if (!canProcessTransaction(transaction)) {
      throw new UnsupportedTransactionException(
          "Card not supported for this " + this.getClass().getName());
    }
    if (transaction.getCardType().equals(CardType.DinersClub)
        && !transaction.getCountry().getCountryCode().equalsIgnoreCase(india().getCountryCode())) {
      return transaction.getTransactionAmount() * QUICKPAY_FEE_FOR_DINNERS_NOT_FROM_INDIA;
    }
    if (transaction.getCardType().equals(CardType.DinersClub)
        && transaction.getCountry().getCountryCode().equalsIgnoreCase(india().getCountryCode())) {
      return transaction.getTransactionAmount() * QUICKPAY_FEE_FOR_DINNERS_FROM_INDIA;
    }
    return transaction.getTransactionAmount() * QUICKPAY_FEE_FOR_ALL_OTHER_CARDS;
  }

  @Override
  public TransactionResult processTransaction(Transaction transaction)
      throws UnsupportedTransactionException {
    if (canProcessTransaction(transaction)) {
      double totalFee = transaction.getTransactionAmount() * ORANGE_FEE;
      double receiverWillGet = transaction.getTransactionAmount() - totalFee;
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
        getName(),
        TransactionStatus.Failed,
        0,
        "Card not supported.",
        transaction,
        totalFee,
        0.0,
        0.0);
  }
}
