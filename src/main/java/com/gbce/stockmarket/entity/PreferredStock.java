package com.gbce.stockmarket.entity;

import java.math.BigDecimal;

import static com.gbce.stockmarket.Constants.CENTS_PRECISION;
import static com.gbce.stockmarket.Constants.ROUNDING_POLICY;


public class PreferredStock extends Stock {
    private StockType stockType;
    private double fixedDividendPercentage;

    public PreferredStock(String stockSymbol, double parValue, double dividend, double fixedDividendPercentage) {
        super(stockSymbol, parValue, dividend);
        if (fixedDividendPercentage < 0) {
            throw new IllegalArgumentException("Incorrect argument provided " + "fixedDividendPercentage=" + fixedDividendPercentage);
        }
        this.fixedDividendPercentage = fixedDividendPercentage;
        this.stockType = StockType.PREFERRED;
    }


    @Override
    public BigDecimal getDividend() {
        return new BigDecimal(getParValue()).
                setScale(CENTS_PRECISION, ROUNDING_POLICY).
                multiply(new BigDecimal(fixedDividendPercentage)).
                divide(new BigDecimal(100), CENTS_PRECISION, ROUNDING_POLICY);
    }

    @Override
    public StockType getStockType() {
        return stockType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PreferredStock that = (PreferredStock) o;

        if (Double.compare(that.fixedDividendPercentage, fixedDividendPercentage) != 0) return false;
        return stockType == that.stockType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + stockType.hashCode();
        temp = Double.doubleToLongBits(fixedDividendPercentage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
