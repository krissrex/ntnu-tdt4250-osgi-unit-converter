package no.ntnu.tdt4250.oving2.gogo;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;
import no.ntnu.tdt4250.oving2.conversion.expression.FieldExpressionConverter;

@Component(service = ConversionCommand.class, property = { "osgi.command.scope=conversion",
		"osgi.command.function=addConversion", "osgi.command.function=removeConversion", "osgi.command.function=list",
		"osgi.command.function=convert" })
public class ConversionCommand {

	public void addConversion(String expression) {
		System.out.println("Adding conversion: " + expression);
		FieldExpressionConverter converter = new FieldExpressionConverter(expression);
		System.out.println("Registering " + converter.conversion());
		Activator.getInstance().registerConverter(converter);
		System.out.println("Converter added: " + converter.conversion());
	}

	public void removeConversion(String from, String to) {
		System.out.println(String.format("Removing converson from %s to %s", from, to));
		Conversion conversion = Conversion.from(from, to);
		Activator.getInstance().unregisterConversion(conversion);
		System.out.println("Removed " + conversion);
	}

	public void list() throws InvalidSyntaxException {
		System.out.println("Listing all conversions");
		BundleContext bundleContext = Activator.getInstance().getBundleContext();
		Collection<ServiceReference<Converter>> serviceReferences = bundleContext.getServiceReferences(Converter.class,
				null);

		for (final ServiceReference<Converter> converterReference : serviceReferences) {
			Converter converter = bundleContext.getService(converterReference);
			System.out.println(converter.conversion());
			bundleContext.ungetService(converterReference);
		}
	}

	public void convert(Double value, String from, String to) throws InvalidSyntaxException {
		System.out.println(String.format("Converting %s %s to %s", value, from, to));
		BundleContext bundleContext = Activator.getInstance().getBundleContext();
		Collection<ServiceReference<Converter>> serviceReferences = bundleContext.getServiceReferences(Converter.class, null);

		boolean hasFoundConverter = false;
		for (final ServiceReference<Converter> converterReference : serviceReferences) {
			Converter converter = bundleContext.getService(converterReference);
			try {
				Conversion conversion = converter.conversion();
				if (conversion.from().equals(from) && conversion.to().equals(to)) {
					hasFoundConverter = true;
					System.out.println(converter.convert(value));
					break;
				}
			} finally {
				bundleContext.ungetService(converterReference);
			}
		}
		if (!hasFoundConverter) {
			System.out.println(String.format("No converter found for %s to %s", from, to));
		}
			
	}

}
