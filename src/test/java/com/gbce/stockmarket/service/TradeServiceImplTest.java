package com.gbce.stockmarket.service;

import com.gbce.stockmarket.entity.CommonStock;
import com.gbce.stockmarket.entity.PreferredStock;
import com.gbce.stockmarket.entity.Trade;
import com.gbce.stockmarket.exception.UnknownStockException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

public class TradeServiceImplTest {
    private DatasourceService datasourceService;
    private StockService stockService;
    private TradeService tradeService;

    @Before
    public void setUp() {
        instantiateServices();
        createStocks();
    }

    private void instantiateServices() {
        datasourceService = new CacheDatasourceServiceImpl();
        stockService = new StockServiceImpl(datasourceService);
        tradeService = new TradeServiceImpl(datasourceService, stockService);
    }

    private void createStocks() {
        stockService.addStock(new CommonStock("TEA", 100, 0));
        stockService.addStock(new CommonStock("POP", 100, 8));
        stockService.addStock(new CommonStock("ALE", 60, 23));
        stockService.addStock(new PreferredStock("GIN", 100, 8, 2));
        stockService.addStock(new CommonStock("JOE", 250, 13));
    }

    @Test
    public void bookTradeWhenValidTrade() throws UnknownStockException {
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12);
        
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTradeWhenInValidSymbol() throws UnknownStockException {
        tradeService.bookTrade("", 100l, "BUY", 110.12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTradeWhenInValidQuantity() throws UnknownStockException {
        tradeService.bookTrade("TEA", 0, "BUY", 110.12);
        
    }


    @Test
    public void getTradesWithinTimeWindow() throws UnknownStockException, InterruptedException {
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12d);
        Thread.sleep(20000);
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12d);
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12d);
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12d);

        Collection<Trade> trades = tradeService.getTradesWithinTimeWindow("TEA", 0.25);
        assertEquals("Should return 3 trades", 3, trades.size());
        assertEquals("The received trade should have symbol", "TEA", trades.iterator().next().getStock().getStockSymbol());
    }


    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenGetTradesWithinTimeWindowWithInvalidTime() throws UnknownStockException {
        Collection<Trade> trades = tradeService.getTradesWithinTimeWindow("TEA", -1);
        assertEquals("The received trade should have symbol", "TEA", trades.iterator().next().getStock().getStockSymbol());
    }


    @Test
    public void GetAllTradesForStock() throws UnknownStockException {
        tradeService.bookTrade("TEA", 100l, "BUY", 110.12);
        Collection<Trade> trades = tradeService.getAllTradesForStock("TEA");
        assertEquals("The received trade should have symbol", "TEA", trades.iterator().next().getStock().getStockSymbol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenGetAllTradesForStockWithInvalidSymbol() throws UnknownStockException {
        tradeService.getAllTradesForStock("");

    }

}
