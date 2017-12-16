package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.entity.Trade;

import java.util.Collection;


interface DatasourceService {

    void storeTrade(Trade trade);

    void storeStock(Stock stock);

    Stock getStock(String stockSymbol);

    Collection<Trade> getTradesForStock(Stock stock);

    Collection<Stock> getAllStocks();

}
