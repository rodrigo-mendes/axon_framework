package com.kmm.ordermanagement.core.domain.model.entities;

import java.util.Objects;

public class DeliveryAddress {
  
  private String street;
  private String city;
  private String state;
  private String zipCode;
  
  public DeliveryAddress() {
  }
  
  public DeliveryAddress(String street, String city, String state, String zipCode) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }
  
  public String getStreet() {
    return street;
  }
  
  public void setStreet(String street) {
    this.street = street;
  }
  
  public String getCity() {
    return city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public String getState() {
    return state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public String getZipCode() {
    return zipCode;
  }
  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DeliveryAddress that)) {
      return false;
    }
	  return Objects.equals(getStreet(), that.getStreet()) && Objects.equals(
        getCity(), that.getCity()) && Objects.equals(getState(), that.getState())
        && Objects.equals(getZipCode(), that.getZipCode());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getStreet(), getCity(), getState(), getZipCode());
  }
  
  @Override
  public String toString() {
    return "DeliveryAddress{" +
        "street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        '}';
  }
}
