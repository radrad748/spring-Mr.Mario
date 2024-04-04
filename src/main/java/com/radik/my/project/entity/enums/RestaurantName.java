package com.radik.my.project.entity.enums;

public enum RestaurantName {
    THE_LOT("The lot");

    private final String value;

    RestaurantName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
