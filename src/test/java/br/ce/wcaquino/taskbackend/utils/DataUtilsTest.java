package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DataUtilsTest {
	
	@Test
	public void willReturnTrueForFutureDates() {
		LocalDate date = LocalDate.of(2030, 10, 1);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void willReturnFalseForPastDates() {
		LocalDate date = LocalDate.of(2010, 10, 1);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void willReturnTrueForPresentDates() {
		LocalDate date = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
}
