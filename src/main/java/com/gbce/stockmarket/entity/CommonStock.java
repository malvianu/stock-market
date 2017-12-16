package com.gbce.stockmarket.entity;

public class CommonStock extends Stock {
    private StockType stockType;

    public CommonStock(String stockSymbol, double parValue, double dividend) {
        super(stockSymbol, parValue, dividend);
        this.stockType = StockType.COMMON;
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

        CommonStock that = (CommonStock) o;

        return stockType == that.stockType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + stockType.hashCode();
        return result;
    }


}
