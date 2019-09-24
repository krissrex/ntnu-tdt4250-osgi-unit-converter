package no.ntnu.tdt4250.oving2.conversion.expression;

import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.junit.Test;

import jdk.jfr.Experimental;

import net.bytebuddy.asm.Advice.Thrown;
import no.ntnu.tdt4250.oving2.conversion.Conversion;
import net.bytebuddy.asm.Advice.Thrown;

public class FieldExpressionConverterTest {

	@Test
	public void testCorrectExpression() {
		new FieldExpressionConverter("F =   C * 1.8+ 32");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLeftSide() {
		new FieldExpressionConverter("4 =   C * 1.8+ 32");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNoFromm() {
		new FieldExpressionConverter("F = 4 * 1.8 + 32");
	}
	
	@Test
	public void testConversionFieldExtractionFromExpression() {
		Conversion conversion = new FieldExpressionConverter("F = C * 1.8 + 32").conversion();
		
		assertThat(conversion.from(), Is.is("C"));
		assertThat(conversion.to(), Is.is("F"));
	}
	
	@Test
	public void testEvaluation() {
		double actual = new FieldExpressionConverter("F = C * 1.8 + 32").convert(20);
		
		assertThat(actual, Is.is(68.0));
	}
}
