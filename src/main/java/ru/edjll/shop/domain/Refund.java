package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "date",
            length = 10,
            nullable = false
    )
    private Date date;

    @NotEmpty(message = "error.validation.refund.reason.empty")
    @Column(
            name = "reason",
            nullable = false
    )
    private String reason;

    @Column(
            name = "comment"
    )
    private String comment;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "sale_product_id", nullable = false)
    private SaleProduct saleProduct;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "refund_sale_product_id")
    private SaleProduct refundSaleProduct;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_refund")
    private StatusReviewAndRefund statusRefund;

    @Enumerated(EnumType.ORDINAL)
    private TypeRefund typeRefund;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public SaleProduct getSaleProduct() {
        return saleProduct;
    }

    public void setSaleProduct(SaleProduct saleProduct) {
        this.saleProduct = saleProduct;
    }

    public SaleProduct getRefundSaleProduct() {
        return refundSaleProduct;
    }

    public void setRefundSaleProduct(SaleProduct refundSaleProduct) {
        this.refundSaleProduct = refundSaleProduct;
    }

    public StatusReviewAndRefund getStatusRefund() {
        return statusRefund;
    }

    public void setStatusRefund(StatusReviewAndRefund statusRefund) {
        this.statusRefund = statusRefund;
    }

    public TypeRefund getTypeRefund() {
        return typeRefund;
    }

    public void setTypeRefund(TypeRefund typeRefund) {
        this.typeRefund = typeRefund;
    }
}
