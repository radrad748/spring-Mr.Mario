package com.radik.my.project.entity.enums;

public enum PeriodOrders {
    WEEK("week"),
    MONTH("month"),
    YEAR("year"),
    ALL_THE_TIME("all");

    private final String value;

    PeriodOrders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
