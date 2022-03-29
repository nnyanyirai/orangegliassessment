package com.orange.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.orange.Country;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.providers.SurePay;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurePayUTest {
  @InjectMocks private SurePay surePay;
  @Mock private Transaction transaction;

  @Test
  public void givenCountryIsIndiaAndCardIsSurePay_canProcessTransaction_thenReturnFalse() {
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    assertFalse(surePay.canProcessTransaction(transaction));
  }

  @Test
  public void givenCountryIsNotIndiaAndCardIsNotSurePay_canProcessTransaction_thenReturnTrue() {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getCardType()).thenReturn(CardType.MasterCard);
    assertTrue(surePay.canProcessTransaction(transaction));
  }

  @Test
  public void
      givenCountryIsSouthAfrica_andCardTypeSurePay_calculateTransactionFee_thenReturnFourty()
          throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    double delta = 0;
    assertEquals(40.0, surePay.calculateTransactionFee(transaction), delta);
  }

  @Test
  public void
      givenCountryIsSouthAfrica_andCardTypeIsVisa_andAmountIsGreaterLessThanTo500_calculateTransactionFee_thenReturnTwelve()
          throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(400);
    when(transaction.getCardType()).thenReturn(CardType.Visa);
    double delta = 0;
    assertEquals(12.0, surePay.calculateTransactionFee(transaction), delta);
  }
}
