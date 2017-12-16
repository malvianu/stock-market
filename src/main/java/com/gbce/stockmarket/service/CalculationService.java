package com.gbce.stockmarket.service;

import com.gbce.stockmarket.exception.UknownGBCEAllShareIndex;
import com.gbce.stockmarket.exception.UnknownPEException;
import com.gbce.stockmarket.exception.UnknownStockException;


public interface CalculationService {
    double getDividendYield(String stockSymbol, double price) throws UnknownStockException;

    double getPERatio(String stockSymbol, double price) throws UnknownStockException, UnknownPEException;

    double getVolumeWeightedPrice(String stockSymbol, int minutes) throws UnknownStockException;

    double getGBCEAllShareIndex() throws UknownGBCEAllShareIndex;


}
