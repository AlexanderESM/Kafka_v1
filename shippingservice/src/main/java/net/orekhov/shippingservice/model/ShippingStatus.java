package net.orekhov.shippingservice.model;

public enum ShippingStatus {

    NOT_SHIPPED("not_shipped"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    private final String status;

    ShippingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
