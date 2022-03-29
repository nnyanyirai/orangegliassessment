package com.orange.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import com.orange.Country;
import com.orange.exceptions.UnsupportedTransactionException;
import com.orange.payments.providers.HoutPay;
import com.orange.transactions.CardType;
import com.orange.transactions.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HoutPayUTest {
  @InjectMocks
  private HoutPay houtPay;

  @Mock
  private Transaction transaction;

  @Test
  public void givenCountryIsSouthAfricaAndCardIsHoutPay_canProcessTransaction_thenReturnTrue(){
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    assertTrue(houtPay.canProcessTransaction(transaction));
  }


  @Test
  public void givenCountryIsNotSouthAfricaAndCardIsHoutPay_canProcessTransaction_thenReturnFalse(){
    when(transaction.getCountry()).thenReturn(Country.india());
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    assertFalse(houtPay.canProcessTransaction(transaction));
  }

  @Test
  public void givenCountryIsSouthAfrica_andCardTypeHoutPay_calculateTransactionFee_thenReturnTwenty()
      throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.southAfrica());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.HoutPay);
    double delta = 0;
    assertEquals(20.0 , houtPay.calculateTransactionFee(transaction), delta);
  }

  @Test
  public void givenCountryIsNotSouthAfrica_andCardTypeNotHoutPay_calculateTransactionFee_thenReturnTwenty()
      throws UnsupportedTransactionException {
    when(transaction.getCountry()).thenReturn(Country.uk());
    when(transaction.getTransactionAmount()).thenReturn(1000);
    when(transaction.getCardType()).thenReturn(CardType.MasterCard);
    double delta = 0;
    assertEquals(35.0 , houtPay.calculateTransactionFee(transaction), delta);
  }
}
