package com.orange.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.orange.Country;
import com.orange.payments.PaymentProcessor;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.payments.providers.HoutPay;
import com.orange.payments.providers.SurePay;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessorUTest {

  @InjectMocks private PaymentProcessor processor;

  @Mock private Transaction transaction;
  /**
   * Test Scenario 1. HoutPay card 2. Amount - $100 3. Provider - HoutPay 4. Country - SouthAfrica
   */
  @Test
  public void givenValidHoutPayCardFromSouthAfrica_determineProvider_thenReturnHoutPayProvider() {

    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(100);
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    double delta = 0;
    TransactionResult transactionResult = processor.processPayment(transaction);
    assertEquals(2.0, transactionResult.getTransactionFee(), delta);
    assertEquals(95.5, transactionResult.getReceiverWillGet(), delta);
    assertEquals(TransactionStatus.Success, transactionResult.getStatus());
    assertEquals(HoutPay.class.getSimpleName(), transactionResult.getProviderName());
  }

  /**
   * Test Scenario 1. HoutPay card 2. Amount - $1000 3. Provider - SurePay 4. Country - SouthAfrica
   */
  @Test
  public void givenValidTransaction_determineProvider_thenReturnSurePayProvider() {

    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.MasterCard);
    double delta = 0;
    TransactionResult transactionResult = processor.processPayment(transaction);
    assertEquals(25.0, transactionResult.getTransactionFee(), delta);
    assertEquals(930.0, transactionResult.getReceiverWillGet(), delta);
    assertEquals(TransactionStatus.Success, transactionResult.getStatus());
    assertEquals(SurePay.class.getSimpleName(), transactionResult.getProviderName());
  }
}
