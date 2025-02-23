package com.components;

public enum SortOrder {
    NAME_ASC("Name (A to Z)"),
    NAME_DESC("Name (Z to A)"),
    PRICE_ASC("Price (low to high)"),
    PRICE_DESC("Price (high to low)");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getSortValue() {
        return value;
    }
}
