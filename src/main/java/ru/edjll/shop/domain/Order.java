package ru.edjll.shop.domain;

public enum Order {
    DESCENDING("По убыванию цены"), ASCENDING("По возрастанию цены");

    private String text;

    Order(String text) { this.text = text; }

    public String getText() {
        return text;
    }
}
