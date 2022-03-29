package com.orange.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import com.orange.Country;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.TransactionResult;
import com.orange.payments.TransactionStatus;
import com.orange.payments.providers.QuickPay;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuickPayUTest {

  @InjectMocks
  private QuickPay quickPay;

  @Mock
  private Transaction transaction;

  @Test
  public void givenCountryIsSouthAfrica_canProcessTransaction_thenReturnFalse(){
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    assertFalse(quickPay.canProcessTransaction(transaction));
  }

  @Test
  public void givenCountryIsNotSouthAfrica_canProcessTransaction_thenReturnFalse(){
    when(transaction.getCountry()).thenReturn(Country.india());
    assertTrue(quickPay.canProcessTransaction(transaction));
  }

  @Test(expected = UnsupportedTransactionException.class)
  public void givenCountryIsSouthAfrica_calculateTransactionFee_thenReturnMinusOne() throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    quickPay.calculateTransactionFee(transaction);
  }

  @Test
  public void givenCountryIsNotIndia_andCardTypeIsDinners_calculateTransactionFee_thenReturnTwentyFive()
      throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.uk());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.DinersClub);
    double delta = 0;
    assertEquals(25.0 , quickPay.calculateTransactionFee(transaction), delta);
  }

  @Test
  public void givenCountryIsIndia_andCardTypeIsDinners_calculateTransactionFee_thenReturnFour() throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getTransactionAmount()).thenReturn(100);
    when(transaction.getCardType()).thenReturn(CardType.DinersClub);
    double delta = 0;
    assertEquals(4.0 , quickPay.calculateTransactionFee(transaction), delta);
  }

  @Test public void givenCountry_andCardTypeNotDinners_calculateTransactionFee_thenReturnThree() throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.india()); when(transaction.getTransactionAmount()).thenReturn(100);
    when(transaction.getCardType()).thenReturn(CardType.MasterCard); double delta = 0;
    assertEquals(3.0 , quickPay.calculateTransactionFee(transaction), delta); }



  /** * Test Scenario
   *  Amount = 100 *
   *  Transaction fee - $4
   *  * Status - True (Success)
   *  * Amount - receiverWillGet(95.5)
   *  */
  @Test public void givenValidTransaction_processTransaction_thenReturnTransactionResult() throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.india()); when(transaction.getTransactionAmount()).thenReturn(100);
    when(transaction.getCardType()).thenReturn(CardType.DinersClub); double delta = 0;
    TransactionResult transactionResult = quickPay.processTransaction(transaction);
    assertEquals(4.0, transactionResult.getTransactionFee(), delta); assertEquals(95.5, transactionResult.getReceiverWillGet(), delta);
    assertEquals(TransactionStatus.Success, transactionResult.getStatus());
  }

  @Test public void givenInvalidTransaction_processTransaction_thenReturnFail() throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    TransactionResult transactionResult = quickPay.processTransaction(transaction);
    assertEquals(TransactionStatus.Failed, transactionResult.getStatus()); }
}
