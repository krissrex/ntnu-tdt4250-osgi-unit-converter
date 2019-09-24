package no.ntnu.tdt4250.oving2.converter.speed;

import org.osgi.service.component.annotations.Component;

import no.ntnu.tdt4250.oving2.conversion.Converter;
import no.ntnu.tdt4250.oving2.conversion.expression.FieldExpressionConverter;

@Component
public class MphToKmhConverter extends FieldExpressionConverter implements Converter {

	public MphToKmhConverter() {
		super("kmh = mph * 1.609344");
	}

	
}
