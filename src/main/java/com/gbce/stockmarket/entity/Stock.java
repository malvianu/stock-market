package com.gbce.stockmarket.entity;

import java.math.BigDecimal;

import static com.gbce.stockmarket.Constants.CENTS_PRECISION;
import static com.gbce.stockmarket.Constants.ROUNDING_POLICY;


public abstract class Stock {
    private String stockSymbol;
    private double parValue;
    private double dividend;

    public Stock(String stockSymbol, double parValue, double dividend) {
        if (stockSymbol == null || stockSymbol.isEmpty() || parValue <= 0 || dividend < 0) {
            throw new IllegalArgumentException("Incorrect argument provided " + "stockSymbol=" + stockSymbol
                    + " parValue=" + parValue + " dividend=" + dividend);
        }
        this.stockSymbol = stockSymbol;
        this.parValue = parValue;
        this.dividend = dividend;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    double getParValue() {
        return parValue;
    }

    public BigDecimal getDividend() {
        return new BigDecimal(dividend).setScale(CENTS_PRECISION, ROUNDING_POLICY);
    }

    public abstract StockType getStockType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (Double.compare(stock.getParValue(), getParValue()) != 0) return false;
        if (Double.compare(stock.getDividend().doubleValue(), getDividend().doubleValue()) != 0) return false;
        return getStockSymbol().equals(stock.getStockSymbol());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getStockSymbol().hashCode();
        temp = Double.doubleToLongBits(getParValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getDividend().doubleValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", parValue=" + parValue +
                ", dividend=" + dividend +
                '}';
    }
}
