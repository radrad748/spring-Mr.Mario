package com.radik.my.project.entity;

public enum RestaurantName {
    THE_LOT("The lot"),
    BAR_GRILL_75("Bar&Grill-75"),
    FRENCH_MARKET("French market");

    private final String value;

    RestaurantName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
