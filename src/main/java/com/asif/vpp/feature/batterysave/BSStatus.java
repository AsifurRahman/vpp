package com.asif.vpp.feature.batterysave;

public enum BSStatus {

    STARTED("Battery save started"),
    FAILED("Battery save failed"),
    SUCCESS("Battery save successful"),;

    private final String message;

    BSStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
