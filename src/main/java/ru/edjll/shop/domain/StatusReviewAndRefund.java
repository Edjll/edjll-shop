package ru.edjll.shop.domain;

public enum StatusReviewAndRefund {
    PROCESSING("Обрабатывается"), CONFIRMED("Утверждён"), UNCONFIRMED("Отклонён");

    private String text;

    StatusReviewAndRefund(String text) { this.text = text; }

    public String getText() {
        return text;
    }
}
