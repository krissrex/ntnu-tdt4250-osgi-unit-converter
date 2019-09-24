package no.ntnu.tdt4250.oving2.conversion.expression;

import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.junit.Test;

import no.ntnu.tdt4250.oving2.conversion.Conversion;

public class ExpressionBasedConverterTest {

	@Test
	public void testConversionFtoC() {
		double actual = ExpressionBasedConverter.evaluateExpression("F = C * 1.8 + 32", 
				Conversion.from("C", "F"),
				20);
		
		assertThat(actual, Is.is(68.0));
	}
	
	@Test
	public void testConversionCtoF() {
		double actual = ExpressionBasedConverter.evaluateExpression("C = (F - 32) / 1.8", 
				Conversion.from("F", "C"),
				68);
		
		assertThat(actual, Is.is(20.0));
	}

	@Test
	public void testBinding() {
		double actual = ExpressionBasedConverter.evaluateExpression("A = B + B + B * B", 
				Conversion.from("B", "A"),
				2);
		
		assertThat(actual, Is.is(8.0));
	}
	
	
	@Test
	public void testConversionFromExpression_from() {
		Conversion conversion = ExpressionBasedConverter.fromExpression("F = C");
		
		assertThat(conversion.from(), Is.is("C"));
	}
	
	@Test
	public void testConversionFromExpression_to() {
		Conversion conversion = ExpressionBasedConverter.fromExpression("F = C");
		
		assertThat(conversion.to(), Is.is("F"));
	}
}
