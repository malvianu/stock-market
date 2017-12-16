package com.gbce.stockmarket.service;


import com.gbce.stockmarket.entity.CommonStock;
import com.gbce.stockmarket.entity.PreferredStock;
import com.gbce.stockmarket.exception.UknownGBCEAllShareIndex;
import com.gbce.stockmarket.exception.UnknownPEException;
import com.gbce.stockmarket.exception.UnknownStockException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculationServiceImplTest {
    private MarketPriceService marketPriceService;
    private DatasourceService datasourceService;
    private StockService stockService;
    private TradeService tradeService;
    private CalculationService calculationService;

    @Before
    public void setUp() {
        instantiateServices();
        createStocks();
        mockMarketPriceServiceBehavior();

    }

    private void mockMarketPriceServiceBehavior() {
        when(marketPriceService.getCurrentMarketPrice("TEA")).thenReturn(100.00);
        when(marketPriceService.getCurrentMarketPrice("POP")).thenReturn(10.00);
        when(marketPriceService.getCurrentMarketPrice("ALE")).thenReturn(12.00);
        when(marketPriceService.getCurrentMarketPrice("GIN")).thenReturn(13.00);
        when(marketPriceService.getCurrentMarketPrice("JOE")).thenReturn(14.00);
    }

    private void createStocks() {
        stockService.addStock(new CommonStock("TEA", 100, 0));
        stockService.addStock(new CommonStock("POP", 100, 8));
        stockService.addStock(new CommonStock("ALE", 60, 23));
        stockService.addStock(new PreferredStock("GIN", 100, 8, 2));
        stockService.addStock(new CommonStock("JOE", 250, 13));
    }

    private void instantiateServices() {
        marketPriceService = mock(MarketPriceService.class);
        datasourceService = new CacheDatasourceServiceImpl();
        stockService = new StockServiceImpl(datasourceService);
        tradeService = new TradeServiceImpl(datasourceService, stockService);
        calculationService = new CalculationServiceImpl(tradeService, stockService, marketPriceService);
    }

    @Test
    public void calculateZeroDividendYieldWhenValidCommonStockAndValidPrice() throws UnknownStockException {
        assertEquals("The calculated dividend yield should be 0.0", 0.0, calculationService.getDividendYield("TEA", 105));
    }

    @Test
    public void calculateNonZeroDividendYieldWhenValidCommonStockAndValidPrice() throws UnknownStockException {
        assertEquals("The calculated dividend yield should be 0.0762", 0.0762, calculationService.getDividendYield("POP", 105));
    }

    @Test
    public void calculateNonZeroDividendYieldWhenValidPreferredStockAndValidPrice() throws UnknownStockException {
        assertEquals("The calculated dividend yield should be 0.0190", 0.0190, calculationService.getDividendYield("GIN", 105));
    }

    @Test(expected = UnknownStockException.class)
    public void throwUnknownStockExceptionWhenInvalidStockSymbol() throws UnknownStockException {
        calculationService.getDividendYield("UNKNOWNSTOCK", 105);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenPriceIsZero() throws UnknownStockException {
        calculationService.getDividendYield("TEA", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenPriceIsNegative() throws UnknownStockException {
        calculationService.getDividendYield("TEA", -7);
    }

    @Test
    public void calcuatePEWhenValidCommonStockAndValidPrice() throws UnknownPEException, UnknownStockException {
        assertEquals("The calculated P/E Ratio should be 13.1250", 13.1250, calculationService.getPERatio("POP", 105));
    }

    @Test
    public void calcuatePEWhenValidPreferredStockAndValidPrice() throws UnknownPEException, UnknownStockException {
        assertEquals("The calculated P/E Ratio should be 52.5000", 52.5000, calculationService.getPERatio("GIN", 105));
    }

    @Test(expected = UnknownPEException.class)
    public void throwUnknownPEExceptionWhenDividendIsZero() throws UnknownPEException, UnknownStockException {
        calculationService.getPERatio("TEA", 105);
    }

    @Test(expected = UnknownStockException.class)
    public void throwUnknownStockExceptionInPECalculationWhenInvalidStockSymbol() throws UnknownStockException, UnknownPEException {
        calculationService.getPERatio("UNKNOWNSTOCK", 105);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionInPECalculationWhenPriceIsZero() throws UnknownStockException, UnknownPEException {
        calculationService.getPERatio("TEA", 0);
    }


    @Test
    public void calculateVolumeWeightedPrice() throws UnknownStockException, UnknownPEException {
        tradeService.bookTrade("TEA", 10, "BUY", 100);
        tradeService.bookTrade("TEA", 10, "BUY", 110);
        tradeService.bookTrade("TEA", 20, "BUY", 120);
        assertEquals("The calculated  volume weighted price should be 112.50", 112.50, calculationService.getVolumeWeightedPrice("TEA", 10));

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionInCalculatingVWPWhenInvalidTimeGiven() throws UnknownStockException, UnknownPEException {
        calculationService.getVolumeWeightedPrice("TEA", -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionInCalculatingVWPWhenInvalidSymbolGiven() throws UnknownStockException, UnknownPEException {
        calculationService.getVolumeWeightedPrice("", 10);
    }

    @Test
    public void calculateGBCEAllShareIndex() throws UknownGBCEAllShareIndex {
        assertEquals("The calculated Index should be 18.53", 18.53, calculationService.getGBCEAllShareIndex());
    }

    @Test(expected = UknownGBCEAllShareIndex.class)
    public void throwUknownGBCEAllShareIndexInCalulatingGBCEAllShareIndex() throws UknownGBCEAllShareIndex {
        instantiateServices();
        calculationService.getGBCEAllShareIndex();
    }

}
