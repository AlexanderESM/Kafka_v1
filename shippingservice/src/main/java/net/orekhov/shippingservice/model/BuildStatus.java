package net.orekhov.shippingservice.model;

public enum BuildStatus {

    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private final String status;

    BuildStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

