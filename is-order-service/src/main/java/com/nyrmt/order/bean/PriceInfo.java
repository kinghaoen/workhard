package com.nyrmt.order.bean;

import java.math.BigDecimal;

public class PriceInfo {
    private Long id;
    private BigDecimal price;

    public PriceInfo() {
    }

    public PriceInfo(Long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
