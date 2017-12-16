package com.gbce.stockmarket.entity;


import java.math.BigDecimal;
import java.util.Date;


public class Trade {
    private final Stock stock;
    private final Direction direction;
    private final BigDecimal price;
    private final long quantity;
    private final long timestamp = new Date().getTime();

    public Trade(Stock stock, Direction direction, BigDecimal price, long quantity) {
        if (stock == null || direction == null || price == null || price.doubleValue() <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Incorrect argument provided " + "stock=" + stock +
                    " direction=" + direction + " price=" + price + " quantity=" + quantity);
        }
        this.stock = stock;
        this.direction = direction;
        this.price = price;
        this.quantity = quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public Direction getDirection() {
        return direction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
