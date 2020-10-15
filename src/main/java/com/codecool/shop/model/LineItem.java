package com.codecool.shop.model;

public class LineItem extends BaseModel{
    private float totalPrice;
    private float unitPrice;
    private int quantity;

    public LineItem(Product product) {
        super();
        this.name = product.getName();
        this.unitPrice = product.getDefaultPrice();
        this.quantity = 1;
        this.totalPrice = unitPrice * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void changeQuantity(int change) throws IllegalArgumentException {
        if (quantity + change <= 0) throw new IllegalArgumentException();
        this.quantity += change;
        this.totalPrice = this.quantity * this.unitPrice;
    }

    public void decreaseQuantity() {
        if (quantity != 0) {
            this.quantity -= 1;
            this.totalPrice = this.quantity * this.unitPrice;
        }
    }
}
