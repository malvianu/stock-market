package com.gbce.stockmarket.entity;

import static junit.framework.TestCase.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class TradeTest {
	@Test
	public void createTradeWhenValidInput() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(20),12l);
		assertEquals("The stock symbol should be TEA", "TEA", trade.getStock().getStockSymbol());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenStockIsInvalid() {
		Trade trade = new Trade(null, Direction.BUY, new BigDecimal(20),12l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenDirectionIsInvalid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, null, new BigDecimal(20),12l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenPriceIsInvalid() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, null, 12l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenPriceIsZero() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, BigDecimal.ZERO, 12l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenPriceIsNegative() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(-132), 12l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenQuantityIsZero() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(20), 0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenQuantityIsNegative() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		Trade trade = new Trade(stock, Direction.BUY, new BigDecimal(20), -12l);
	}

}
