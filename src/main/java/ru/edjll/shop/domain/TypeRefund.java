package ru.edjll.shop.domain;

public enum TypeRefund {
    PRODUCT("Продукт"), MONEY("Деньги");

    private String text;

    TypeRefund(String text) { this.text = text; }

    public String getText() {
        return text;
    }
}
