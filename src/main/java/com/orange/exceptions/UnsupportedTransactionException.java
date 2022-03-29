package com.orange.exceptions;

public class UnsupportedTransactionException extends Exception{

  public UnsupportedTransactionException(String message){
    super(message);
  }
}