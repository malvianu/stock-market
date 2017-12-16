package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.entity.Trade;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;


public class CacheDatasourceServiceImpl implements DatasourceService {
    private final Map<String, Stock> stockCache;
    private final Map<Stock, Collection<Trade>> tradeCache;

    public CacheDatasourceServiceImpl() {
        stockCache = new ConcurrentHashMap<>();
        tradeCache = new ConcurrentHashMap<>();
    }

    @Override
    public void storeTrade(Trade trade) {
    	if(trade == null) {
    		throw new IllegalArgumentException("Invalid Trade provided "+trade);
    	}
        Collection<Trade> trades = new ConcurrentLinkedDeque<>();
        tradeCache.putIfAbsent(trade.getStock(), trades);
        tradeCache.get(trade.getStock()).add(trade);
    }

    @Override
    public void storeStock(Stock stock) {
    	if(stock == null) {
    		throw new IllegalArgumentException("Provided stock is not valid " +stock);
    	}
        stockCache.putIfAbsent(stock.getStockSymbol(), stock);
    }


    @Override
    public Stock getStock(String stockSymbol) {
    	if(stockSymbol == null || stockSymbol.isEmpty()) {
    		throw new IllegalArgumentException("Provided stock symbol is not valid " +stockSymbol);
    	}
        return stockCache.get(stockSymbol);
    }

    @Override
    public Collection<Trade> getTradesForStock(Stock stock) {
    	if(stock == null) {
    		throw new IllegalArgumentException("Provided stock is not valid " +stock);
    	}
    	
        Collection<Trade> trades = tradeCache.get(stock);
        if (trades == null) {
            return Collections.emptyList();
        }
        return trades;
    }

    @Override
    public Collection<Stock> getAllStocks() {
        Collection<Stock> stocks = stockCache.values();
        if (stocks == null) {
            return Collections.emptyList();
        }
        return stocks;
    }
}
