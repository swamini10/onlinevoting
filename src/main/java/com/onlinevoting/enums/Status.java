package com.onlinevoting.enums;

public enum Status  {
    
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
