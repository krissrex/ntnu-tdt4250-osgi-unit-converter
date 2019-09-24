package no.ntnu.tdt4250.oving2.converter.speed;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;

import no.ntnu.tdt4250.oving2.conversion.Conversion;

public class MphToKmhConverterTest {

	@Test
	public void testConversion() {
		MphToKmhConverter converter = new MphToKmhConverter();
		Conversion conversion = converter.conversion();
		
		assertThat(conversion.from(), Is.is("mph"));
		assertThat(conversion.to(), Is.is("kmh"));
	}
	
	@Test
	public void testConversionValue() {
		MphToKmhConverter converter = new MphToKmhConverter();
		double actual = converter.convert(30);
		
		assertThat(actual, Is.is(48.28032));		
	}


}
