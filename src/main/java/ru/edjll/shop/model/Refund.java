package ru.edjll.shop.model;

import ru.edjll.shop.domain.TypeRefund;

public class Refund {
    private Long id;

    private Long saleProduct;

    private String reason;

    private String comment;

    private Integer status;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleProduct() {
        return saleProduct;
    }

    public void setSaleProduct(Long saleProduct) {
        this.saleProduct = saleProduct;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
