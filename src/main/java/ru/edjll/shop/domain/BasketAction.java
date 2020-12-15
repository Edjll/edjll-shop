package ru.edjll.shop.domain;

public enum BasketAction {
    ADD("Продукт успешно добавлен в корзину"),
    UPDATE("Продукт успешно изменён в корзине"),
    REMOVE("Продукт успешно удалён из корзины");

    private String text;

    BasketAction(String text) { this.text = text; }

    public String getText() {
        return text;
    }
}
