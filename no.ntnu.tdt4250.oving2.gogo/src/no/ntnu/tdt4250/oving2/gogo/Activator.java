package no.ntnu.tdt4250.oving2.gogo;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;

public class Activator implements BundleActivator {

	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	private static Activator INSTANCE = null;
	
	private final Map<Conversion, ServiceRegistration<Converter>> converterRegistrations = new HashMap<>();
	
	public Activator() {}
	

	@Override
	public void start(BundleContext context) throws Exception {
		logger.info("Starting bundle " + context.getBundle().getSymbolicName());
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logger.info("Stopping bundle " + context.getBundle().getSymbolicName());
		INSTANCE = null;
	}
	
	public static Activator getInstance() {
		return INSTANCE;
	}
	
	public void registerConverter(Converter converter) {
		logger.info("Registering {}", converter.conversion());
		if (converterRegistrations.containsKey(converter.conversion())) {
			logger.warn("Already has a converter for {}. Removing the old", converter.conversion());
			unregisterConversion(converter.conversion());
		}
		
		BundleContext context = getBundleContext();
		ServiceRegistration<Converter> registration = context.registerService(Converter.class, converter, null);
		converterRegistrations.put(converter.conversion(), registration);
	}
	
	public void unregisterConversion(Conversion conversion) {
		logger.info("Unregistering {}", conversion);
		ServiceRegistration<Converter> registration = converterRegistrations.get(conversion);
		if (registration != null) {
			registration.unregister();
		} else {
			logger.info("No registration found for {}", conversion);
		}
		converterRegistrations.remove(conversion);			
	}
	
	public BundleContext getBundleContext() {
		return FrameworkUtil.getBundle(Activator.class).getBundleContext();
	}
	
}
