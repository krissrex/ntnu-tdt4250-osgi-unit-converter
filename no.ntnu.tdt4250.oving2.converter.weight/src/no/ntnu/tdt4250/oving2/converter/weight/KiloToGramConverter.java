package no.ntnu.tdt4250.oving2.converter.weight;

import org.osgi.service.component.annotations.Component;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;

@Component
public class KiloToGramConverter implements Converter {

	private static final Conversion KG_TO_GRAM = Conversion.from("kg", "g");
	
	@Override
	public Conversion conversion() {
		return KG_TO_GRAM;
	}

	@Override
	public double convert(double value) {
		return value * 1000;
	}

	
}
