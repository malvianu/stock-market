package com.gbce.stockmarket.service;

import static junit.framework.TestCase.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.gbce.stockmarket.entity.CommonStock;
import com.gbce.stockmarket.entity.Direction;
import com.gbce.stockmarket.entity.Stock;
import com.gbce.stockmarket.entity.Trade;

public class CacheDatasourceServiceImplTest {

	private DatasourceService datasourceService;
	
	@Before
    public void setUp() {
        instantiateServices();
        
    }
	
	private void instantiateServices() {
		datasourceService = new CacheDatasourceServiceImpl();
	}
	
	@Test
	public void storeTradeWhenTradeIsValid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(20),12l);
		datasourceService.storeTrade(trade);
		assertEquals("The no of trades for stock symbol TEA should be 1",1, datasourceService.getTradesForStock(stock).size());
	}
	
	private void storeMultipleTrades(Stock stock1, Stock stock2) {
		
		Trade trade1 = new Trade(stock1, Direction.BUY, new BigDecimal(20),12l);
		datasourceService.storeTrade(trade1);
		Trade trade2 = new Trade(stock1, Direction.BUY, new BigDecimal(20),11);
		datasourceService.storeTrade(trade2);
		Trade trade3 = new Trade(stock1, Direction.BUY, new BigDecimal(11),11);
		datasourceService.storeTrade(trade3);
		Trade trade4 = new Trade(stock1, Direction.SELL, new BigDecimal(25),100);
		datasourceService.storeTrade(trade4);
		
		Trade trade5 = new Trade(stock2, Direction.BUY, new BigDecimal(250),10);
		datasourceService.storeTrade(trade5);
		Trade trade6 = new Trade(stock2, Direction.BUY, new BigDecimal(2000),10);
		datasourceService.storeTrade(trade6);
		Trade trade7 = new Trade(stock2, Direction.BUY, new BigDecimal(35),10);
		datasourceService.storeTrade(trade7);
	}
	
	@Test
	public void storeMultipleTradesWithValidInputs() {
		Stock stock1 = new CommonStock("TEA", 100d, 20d);
		Stock stock2 = new CommonStock("POP", 10d, 200d);
		storeMultipleTrades(stock1,stock2);
		assertEquals("The no of trades for stock symbol TEA should be 4",4, datasourceService.getTradesForStock(stock1).size());
		assertEquals("The no of trades for stock symbol POP should be 3",3, datasourceService.getTradesForStock(stock2).size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhileStoreTradeWhenTradeIsInvalid() {
		datasourceService.storeTrade(null);
	}
	
	@Test
	public void storeStockWhenStockIsValid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		datasourceService.storeStock(stock);
		assertEquals("The stock for symbol TEA should be equal to stock",stock, datasourceService.getStock(stock.getStockSymbol()));
	}
	
	@Test
	public void storeMultipleStockWhenStockIsValid() {
		Stock stock1 = new CommonStock("TEA", 100d, 20d);
		Stock stock2 = new CommonStock("POP", 101d, 20d);
		Stock stock3 = new CommonStock("ABC", 10d, 35d);
		datasourceService.storeStock(stock1);
		datasourceService.storeStock(stock2);
		datasourceService.storeStock(stock3);
		assertEquals("The stock for symbol TEA should be equal to stock",stock1, datasourceService.getStock(stock1.getStockSymbol()));
		assertEquals("The stock for symbol POP should be equal to stock",stock2, datasourceService.getStock(stock2.getStockSymbol()));
		assertEquals("The stock for symbol ABC should be equal to stock",stock3, datasourceService.getStock(stock3.getStockSymbol()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhileStoreStockWhenStockIsInValid() {
		datasourceService.storeStock(null);
	}
	
	
	@Test
	public void getStockWhenSymbolIsValid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		datasourceService.storeStock(stock);
		datasourceService.getStock("TEA");
		assertEquals("The stock for symbol TEA should be equal to stock",stock, datasourceService.getStock(stock.getStockSymbol()));
	}
	
	@Test
	public void getStockWhenNoStockForGivenSymbolExists() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		assertEquals("The stock for symbol TEA should be equal to null",null, datasourceService.getStock(stock.getStockSymbol()));
	}
	
	@Test
	public void getTradesForStockWhenStockIsValid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(20),12l);
		datasourceService.storeTrade(trade);
		assertEquals("The no of trades for stock symbol TEA should be 1",1, datasourceService.getTradesForStock(stock).size());
	}
	
	@Test
	public void getTradesForStockWhenNoTradeForGivenStockExists() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		assertEquals("The no of trades for stock symbol TEA should be 0",0, datasourceService.getTradesForStock(stock).size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhileGetTradesForStockWhenStockIsInvalid() {
		datasourceService.getTradesForStock(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhileGetStockWhenSymbolIsInvalid() {
		datasourceService.getStock(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhileGetStockWhenSymbolIsEmpty() {
		datasourceService.getStock("");
	}
	
	private void storeStocks() {
		Stock stock1 = new CommonStock("TEA", 100d, 20d);
		Stock stock2 = new CommonStock("POP", 10d, 28d);
		Stock stock3 = new CommonStock("JPM", 10d, 12d);
		Stock stock4 = new CommonStock("ABC", 15d, 20d);
		Stock stock5 = new CommonStock("GINI", 1d, 2d);
		
		datasourceService.storeStock(stock1);
		datasourceService.storeStock(stock2);
		datasourceService.storeStock(stock3);
		datasourceService.storeStock(stock4);
		datasourceService.storeStock(stock5);
	}
	
	@Test
	public void getAllStocks() {
		storeStocks();
		assertEquals("The no of stocks for stock should be 5",5, datasourceService.getAllStocks().size());
	}

	@Test
	public void getAllStocksWhenNoStockExists() {
		assertEquals("The stock collection should be empty ",true, datasourceService.getAllStocks().isEmpty());
	}
	
}
