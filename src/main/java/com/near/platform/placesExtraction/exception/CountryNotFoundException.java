package com.near.platform.placesExtraction.exception;

public class CountryNotFoundException extends Exception{
  private static final long SerialVersionUID=1L;

  public CountryNotFoundException(String message) {
    super(message);
  }
}
