package com.codecool.shop.model;

public class LineItem extends BaseModel{
    private float unitPrice;
    private int quantity;

    public LineItem(Product product) {
        super();
        this.name = product.getName();
        this.unitPrice = product.getDefaultPrice();
        this.quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getTotalPrice() {
        return unitPrice * quantity;
    }

    public void changeQuantity(int change) throws IllegalArgumentException {
        if (quantity + change <= 0) throw new IllegalArgumentException();
        this.quantity += change;
    }

    public void decreaseQuantity() {
        if (quantity != 0) this.quantity -= 1;
    }
}