package com.orange.transactions;

import com.orange.Country;

import java.util.Arrays;
import java.util.List;

public class Transactions {

    List<Transaction> transactions;

    public Transactions() {
        transactions = Arrays.asList(
                new Transaction(1L, CardType.MasterCard, Country.india(), 1000),
                new Transaction(1L, CardType.HoutPay, Country.southAfrica(), 300),
                new Transaction(1L, CardType.Visa, Country.uk(), 500),
                new Transaction(1L, CardType.DinersClub, Country.usa(), 5000),
                new Transaction(1L, CardType.Visa, Country.india(), 300),
                new Transaction(1L, CardType.HoutPay, Country.usa(), 4500),
                new Transaction(1L, CardType.MasterCard, Country.houtBay(), 1399),
                new Transaction(1L, CardType.DinersClub, Country.uk(), 4000),
                new Transaction(1L, CardType.MasterCard, Country.southAfrica(), 3500),
                new Transaction(1L, CardType.DinersClub, Country.southAfrica(), 1500)
        );
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
