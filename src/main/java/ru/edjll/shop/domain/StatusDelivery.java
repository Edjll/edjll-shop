package ru.edjll.shop.domain;

public enum StatusDelivery {
    PROCESSING("Обрабатывается"), SENT("Отправлено"), DELIVERED("Доставлено");

    private String text;

    StatusDelivery(String text) { this.text = text; }

    public String getText() {
        return text;
    }
}
