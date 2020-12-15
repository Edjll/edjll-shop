package ru.edjll.shop.model.statistics;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    Long id;

    public Sale(LocalDateTime date, Long id) {
        this.date = convertLocalDateTimeToLocalDate(date);
        this.id = id;
    }

    public LocalDate convertLocalDateTimeToLocalDate(LocalDateTime date) {
        return date.toLocalDate();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
