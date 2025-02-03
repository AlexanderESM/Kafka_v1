package net.orekhov.shippingservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Shipping {

    @JsonProperty("paymentId")
    private String id;

    @JsonProperty("shippingStatus")
    private ShippingStatus shippingStatus;

    @JsonProperty("buildStatus")
    private BuildStatus buildStatus;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("product")
    private String product;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private double price;

    // Геттеры и сеттеры

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    // Другие геттеры и сеттеры для остальных полей

}

