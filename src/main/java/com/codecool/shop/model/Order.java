package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel{
    private List<LineItem> lineItems;

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
}
