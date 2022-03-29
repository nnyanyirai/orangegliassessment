package com.orange.payments;

import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.providers.HoutPay;
import com.orange.payments.providers.PayMasters;
import com.orange.payments.providers.QuickPay;
import com.orange.payments.providers.SurePay;
import com.orange.transactions.Transaction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentProcessor {

  private List<PaymentProvider> providers;
  private final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

  public PaymentProcessor() {
    providers = Arrays.asList(new HoutPay(), new SurePay(), new QuickPay(), new PayMasters());
  }

  public TransactionResult processPayment(Transaction transaction) {
    PaymentProvider paymentProvider = null;
    try {
      paymentProvider = determineProvider(transaction);
      return paymentProvider.processTransaction(transaction);
    } catch (UnsupportedTransactionException e) {
      logger.info("Transaction not supported for this {} ", paymentProvider.getName());
    }
    return new TransactionResult(
        null, TransactionStatus.Failed, 0, "Card not supported.", transaction, 0.0, 0.0, 0.0);
  }

  private PaymentProvider determineProvider(Transaction transaction) {
    Map<String, PaymentProvider> providersByName =
        providers.stream()
            .collect(
                Collectors.toMap(PaymentProvider::getName, Function.identity(), (k1, k2) -> k1));

    Map<String, Double> providerCharges = new HashMap<>();
    for (PaymentProvider paymentProvider : providers) {
      try {
        providerCharges.putIfAbsent(
            paymentProvider.getName(), paymentProvider.calculateTransactionFee(transaction));
      } catch (UnsupportedTransactionException e) {
        logger.info("Transaction not supported for {} ", paymentProvider.getName());
      }
    }

    Entry<String, Double> min = null;
    for (Entry<String, Double> entry : providerCharges.entrySet()) {
      if (min == null || min.getValue() > entry.getValue()) {
        min = entry;
      }
    }
    return providersByName.get(min.getKey());
  }
}
