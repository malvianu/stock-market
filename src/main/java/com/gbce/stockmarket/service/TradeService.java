package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Trade;
import com.gbce.stockmarket.exception.UnknownStockException;

import java.util.Collection;


public interface TradeService {

    void bookTrade(String stockSymbol, long quantity, String direction, double price) throws UnknownStockException;

    Collection<Trade> getTradesWithinTimeWindow(String stockSymbol, double minutes) throws UnknownStockException;

    Collection<Trade> getAllTradesForStock(String stockSymbol) throws UnknownStockException;

}
