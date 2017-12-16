package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.entity.Trade;
import com.gbce.stockmarket.exception.UknownGBCEAllShareIndex;
import com.gbce.stockmarket.exception.UnknownPEException;
import com.gbce.stockmarket.exception.UnknownStockException;

import java.math.BigDecimal;
import java.util.Collection;

import static com.gbce.stockmarket.Constants.*;


public class CalculationServiceImpl implements CalculationService {

    private final TradeService tradeService;
    private final StockService stockService;
    private final MarketPriceService marketPriceService;

    public CalculationServiceImpl(TradeService tradeService, StockService stockService, MarketPriceService marketPriceService) {
        this.tradeService = tradeService;
        this.stockService = stockService;
        this.marketPriceService = marketPriceService;
    }

    @Override
    public double getDividendYield(String stockSymbol, double price) throws UnknownStockException {
        Stock stock = validatePriceAndGetStock(stockSymbol, price);
        return calculateDividendYield(price, stock);
    }

    private double calculateDividendYield(double price, Stock stock) {
        return stock.getDividend().divide(new BigDecimal(price), FRACTION_PRECISION, ROUNDING_POLICY).doubleValue();
    }

    private Stock validatePriceAndGetStock(String stockSymbol, double price) throws UnknownStockException {
        validatePrice(price);
        return stockService.getStock(stockSymbol);
    }

    private void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Invalid price=" + price + " is provided.");
        }
    }

    @Override
    public double getPERatio(String stockSymbol, double price) throws UnknownStockException, UnknownPEException {
        Stock stock = validatePriceAndGetStock(stockSymbol, price);
        BigDecimal dividend = stock.getDividend();
        if (dividend == null || dividend.doubleValue() == 0) {
            throw new UnknownPEException("P/E Ratio can't be calculated for stock " + stockSymbol + " with dividend = " + dividend);
        }
        return new BigDecimal(price).divide(dividend, FRACTION_PRECISION, ROUNDING_POLICY).doubleValue();

    }

    @Override
    public double getVolumeWeightedPrice(String stockSymbol, int minutes) throws UnknownStockException {
        Collection<Trade> trades = tradeService.getTradesWithinTimeWindow(stockSymbol, minutes);
        BigDecimal sumOfPriceMultipliedByQuantity = BigDecimal.ZERO.setScale(CENTS_PRECISION, ROUNDING_POLICY);
        long totalQuantity = 0;

        for (Trade trade : trades) {
            sumOfPriceMultipliedByQuantity = sumOfPriceMultipliedByQuantity.add(trade.getPrice().multiply(new BigDecimal(trade.getQuantity())));
            totalQuantity += trade.getQuantity();
        }
        return sumOfPriceMultipliedByQuantity.divide(new BigDecimal(totalQuantity), CENTS_PRECISION, ROUNDING_POLICY).doubleValue();
    }

    @Override
    public double getGBCEAllShareIndex() throws UknownGBCEAllShareIndex {
        Collection<Stock> stocks = stockService.getAllStocksOfExchange();
        if (stocks == null || stocks.isEmpty()) {
            throw new UknownGBCEAllShareIndex("Index can't be calculated");
        }
        BigDecimal product = BigDecimal.ONE;
        for (Stock stock : stocks) {
            product = product.multiply(new BigDecimal(marketPriceService.getCurrentMarketPrice(stock.getStockSymbol())).setScale(CENTS_PRECISION, ROUNDING_POLICY));
        }
        double index = Math.pow(product.doubleValue(), BigDecimal.ONE.divide(new BigDecimal(stocks.size())).doubleValue());
        return new BigDecimal(index).setScale(CENTS_PRECISION, ROUNDING_POLICY).doubleValue();
    }
}
