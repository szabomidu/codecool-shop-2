package com.codecool.shop.model;

public class OrderData {
    private int orderId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int zipCode;
    private String city;
    private String country;

    public int getOrderId() {
        return orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

}
