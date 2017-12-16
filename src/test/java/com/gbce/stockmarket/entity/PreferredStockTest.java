package com.gbce.stockmarket.entity;

import static com.gbce.stockmarket.Constants.CENTS_PRECISION;
import static com.gbce.stockmarket.Constants.ROUNDING_POLICY;
import static junit.framework.TestCase.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class PreferredStockTest {
	
	@Test
	public void createPreferredStockWhenValidInput() {
		Stock stock = new PreferredStock("TEA", 120d, 20d, 5);
		assertEquals("The stock symbol should be TEA", "TEA", stock.getStockSymbol());
		assertEquals("The stock type should be Preferred", StockType.PREFERRED, stock.getStockType());
		assertEquals("The stock par value should be 120.0", new BigDecimal(120.0).doubleValue(), stock.getParValue());
		assertEquals("The stock dividend should be 6.00", new BigDecimal(6.0).setScale(CENTS_PRECISION, ROUNDING_POLICY), stock.getDividend());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsEmptyWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock("", 120d, 20d, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenSymbolIsInvalidWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock(null, 120d, 20d, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenParValueIsNegativeWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock("TEA", -120d, 20d, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenParValueIsZeroWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock("TEA", 0d, 20d, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenDividendIsNegativeWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock("TEA", 100d, -20d, 5);
	}
	
	@Test
	public void createPreferredStockWhenDividendIsZero() {
		Stock stock = new PreferredStock("TEA", 120d, 0d, 5);
		assertEquals("The stock symbol should be TEA", "TEA", stock.getStockSymbol());
		assertEquals("The stock type should be Preferred", StockType.PREFERRED, stock.getStockType());
		assertEquals("The stock dividend should be 6.00", new BigDecimal(6.0).setScale(CENTS_PRECISION, ROUNDING_POLICY), stock.getDividend());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionWhenFixedDividendPercentageIsNegativeWhileCreatingPreferredStock() {
		Stock stock = new PreferredStock("TEA", 120d, 20d, -5);
	}
	
	@Test
	public void createPreferredStockWhenFixedDividendPercentageIsZero() {
		Stock stock = new PreferredStock("TEA", 120d, 20d, 0);
		assertEquals("The stock symbol should be TEA", "TEA", stock.getStockSymbol());
		assertEquals("The stock type should be Preferred", StockType.PREFERRED, stock.getStockType());
		assertEquals("The stock divident should be 0.00", new BigDecimal(0.0).setScale(CENTS_PRECISION, ROUNDING_POLICY), stock.getDividend());
	}

}
