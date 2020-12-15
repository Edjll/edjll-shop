package ru.edjll.shop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {

    private LocalDate date;

    private Long count;

    public Sale(LocalDateTime date, Long count) {
        this.date = date.toLocalDate();
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
