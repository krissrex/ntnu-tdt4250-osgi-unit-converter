package no.ntnu.tdt4250.oving2.converter.weight;

import org.osgi.service.component.annotations.Component;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;

@Component
public class GramToKiloConverter implements Converter {

	private static final Conversion GRAM_TO_KG = Conversion.from("g", "kg");
	
	@Override
	public Conversion conversion() {
		return GRAM_TO_KG;
	}

	@Override
	public double convert(double value) {
		return value / 1000;
	}

	
}
