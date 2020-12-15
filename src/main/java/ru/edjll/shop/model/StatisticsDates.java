package ru.edjll.shop.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class StatisticsDates {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
}
