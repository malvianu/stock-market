package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.exception.UnknownStockException;

import java.util.Collection;


public interface StockService {
    Stock getStock(String stockSymbol) throws UnknownStockException;

    void addStock(Stock stock);

    Collection<Stock> getAllStocksOfExchange();
}
