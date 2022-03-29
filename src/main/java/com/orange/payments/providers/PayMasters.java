package com.orange.payments.providers;

import static com.orange.constants.AppConstants.FEE_FOR_PAY_MASTERS;
import static com.orange.constants.AppConstants.ORANGE_FEE;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.PaymentProvider;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;

public class PayMasters implements PaymentProvider {

  @Override
  public String getName() {

    return this.getClass().getSimpleName();
  }

  @Override
  public boolean canProcessTransaction(Transaction transaction) {
    if(transaction.getCardType().equals(CardType.Visa) || transaction.getCardType().equals(CardType.MasterCard));
    return true;
  }

  @Override
  public double calculateTransactionFee(Transaction transaction) throws UnsupportedTransactionException {
    if(canProcessTransaction(transaction))
    {
      return transaction.getTransactionAmount() * FEE_FOR_PAY_MASTERS;
    }
    throw new UnsupportedTransactionException("Card not supported for this " + this.getClass().getName());
  }


  @Override
  public TransactionResult processTransaction(Transaction transaction) throws UnsupportedTransactionException{
    if (canProcessTransaction(transaction)) {
      double totalFee = transaction.getTransactionAmount() * ORANGE_FEE;
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
        transaction, 0.0, 0.0, 0.0);
  }
}
