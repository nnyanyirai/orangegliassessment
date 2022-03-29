package com.orange.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.orange.Country;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.providers.PayMasters;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PayMastersUTest {

  @InjectMocks private PayMasters payMasters;

  @Mock private Transaction transaction;

  @Test
  public void givenAnyCountryAndCardIsMasterCard_canProcessTransaction_thenReturnTrue(){
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getCardType()).thenReturn(CardType.MasterCard);
    assertTrue(payMasters.canProcessTransaction(transaction));
  }

  @Test
  public void givenAnyCountryAndCardIsVisa_canProcessTransaction_thenReturnTrue(){
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getCardType()).thenReturn(CardType.Visa);
    assertTrue(payMasters.canProcessTransaction(transaction));
  }

  @Test
  public void givenAnyCountryAndCardIsHoutPay_canProcessTransaction_thenReturnFalse(){
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    assertFalse(payMasters.canProcessTransaction(transaction));
  }


  @Test
  public void givenAnyCountry_andCardTypeIsVisa_calculateTransactionFee_thenReturnThirty()
      throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.MasterCard);
    double delta = 0;
    assertEquals(30.0 , payMasters.calculateTransactionFee(transaction), delta);
  }
}
