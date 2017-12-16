package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.exception.UnknownStockException;

import java.util.Collection;


public class StockServiceImpl implements StockService {
    private final DatasourceService datasourceService;

    public StockServiceImpl(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @Override
    public Stock getStock(String stockSymbol) throws UnknownStockException {
        if (stockSymbol == null || stockSymbol.isEmpty()) {
            throw new IllegalArgumentException("Invalid stock symbol " + stockSymbol + " provided");
        }
        Stock stock = datasourceService.getStock(stockSymbol);
        if (stock == null) {
            throw new UnknownStockException("No stock with given symbol " + stockSymbol + " exists");
        }
        return stock;
    }

    @Override
    public void addStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Invalid stock " + stock + " provided");
        }
        datasourceService.storeStock(stock);
    }

    @Override
    public Collection<Stock> getAllStocksOfExchange() {
        return datasourceService.getAllStocks();
    }
}
