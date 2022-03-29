package com.orange;

public class Country {
  private final String name;
  private final String countryCode;

  public Country(String name, String countryCode) {
    this.name = name;
    this.countryCode = countryCode;
  }

  public String getName() {
    return name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public static Country southAfrica() {
    return new Country("South Africa", "ZA");
  }

  public static Country usa() {
    return new Country("United States of America", "US");
  }

  public static Country uk() {
    return new Country("United Kingdom", "UK");
  }

  public static Country india() {
    return new Country("India", "IN");
  }

  public static Country houtBay() {
    return new Country("Republic of Hout Bay", "HB");
  }
}
