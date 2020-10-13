package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel{
    private List<LineItem> lineItems;

    public Order(String name) {
        super(name);
        this.lineItems = new ArrayList<>();
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
}
