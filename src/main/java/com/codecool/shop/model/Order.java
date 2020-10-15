package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int zipCode;
    private String city;
    private String country;

    private final List<LineItem> lineItems;

    public Order() {
        super();
        this.lineItems = new ArrayList<>();
    }

    public LineItem findLineItemForProduct(Product product){
        return lineItems.stream().filter(e -> e.getName().equals(product.getName())).findFirst().orElse(null);
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public void removeLineItem(LineItem lineItem) {
        this.lineItems.remove(lineItem);
    }

    public void saveData(OrderData orderData) {
        firstName = orderData.getFirstName();
        lastName = orderData.getLastName();
        email = orderData.getEmail();
        address = orderData.getAddress();
        zipCode = orderData.getZipCode();
        city = orderData.getCity();
        country = orderData.getCountry();
    }
}
