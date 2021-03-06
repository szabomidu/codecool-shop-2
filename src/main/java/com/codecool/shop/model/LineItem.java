package com.codecool.shop.model;

public class LineItem extends BaseModel {
    private int productId;
    private int orderId;
    private float totalPrice;
    private float unitPrice;
    private int quantity;

    public LineItem(Product product, int orderId) {
        super();
        this.productId = product.getId();
        this.orderId = orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void changeQuantity(int change) {
        if (quantity != 0) {
            this.quantity += change;
            setTotalPrice();
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setTotalPrice();
    }

    private void setTotalPrice() {
        this.totalPrice = this.quantity * this.unitPrice;
    }
}
