package com.gbce.stockmarket.entity;

import static com.gbce.stockmarket.Constants.CENTS_PRECISION;
import static com.gbce.stockmarket.Constants.ROUNDING_POLICY;
import static junit.framework.TestCase.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class CommonStockTest {

	@Test
	public void createStockWhenValidInput() {
		Stock stock = new CommonStock("TEA", 100d, 20d);
		assertEquals("The stock symbol should be TEA", "TEA", stock.getStockSymbol());
		assertEquals("The stock type should be Common", StockType.COMMON, stock.getStockType());
		assertEquals("The stock dividend should be 20.0", new BigDecimal(20.0).setScale(CENTS_PRECISION, ROUNDING_POLICY), stock.getDividend());
		assertEquals("The stock par value should be 100.0", new BigDecimal(100.0).doubleValue(), stock.getParValue());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsBlank() {
		Stock stock = new CommonStock("", 100d, 20d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsInvalid() {
		Stock stock = new CommonStock(null, 100d, 20d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenParValueIsNegative() {
		Stock stock = new CommonStock("TEA", -100d, 20d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenParValueIsZero() {
		Stock stock = new CommonStock("TEA", 0d, 20d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenDividendIsNegative() {
		Stock stock = new CommonStock("TEA", 100d, -20d);
	}
	
	@Test
	public void createStockWhenDividendIsZero() {
		Stock stock = new CommonStock("TEA", 100d, 0d);
		assertEquals("The stock symbol should be TEA", "TEA", stock.getStockSymbol());
		assertEquals("The stock type should be Preferred", StockType.COMMON, stock.getStockType());
		assertEquals("The stock divident should be 0.00", new BigDecimal(0.0).setScale(CENTS_PRECISION, ROUNDING_POLICY), stock.getDividend());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsEmptyDividendIsNegativeParValueIsNegative() {
		Stock stock = new CommonStock("", -100d,-12d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsNullDividendIsNegativeParValueIsZero() {
		Stock stock = new CommonStock(null, 0d,-1d);
	}
}
