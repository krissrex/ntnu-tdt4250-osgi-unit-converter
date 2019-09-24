package no.ntnu.tdt4250.oving2.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;

@Component(service = ConversionResource.class)
@JaxrsResource
@Path("/convert")
public class ConversionResource {

	@Reference(service = Converter.class, policy = ReferencePolicy.DYNAMIC, bind = "addConverter", unbind = "removeConverter", cardinality = ReferenceCardinality.MULTIPLE)
	volatile Map<Conversion, Converter> converters = new HashMap<>();

	Logger logger = LoggerFactory.getLogger(ConversionResource.class);

	void addConverter(Converter converter) {
		logger.info("Registered converter {}", converter.conversion());
		converters.put(converter.conversion(), converter);
	}

	void removeConverter(Converter converter) {
		logger.info("Unregistered converter {}", converter.conversion());
		converters.remove(converter.conversion(), converter);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String convert(@QueryParam("from") String from, @QueryParam("to") String to,
			@QueryParam("value") Double value) {

		logger.info("Convert called with from={}, to={}, value={}", new Object[] { from, to, value });

		if (from == null || to == null || value == null) {
			return String.format("Missing parameter! Got: from=%s; to=%s; value=%s", from, to, value);
		}

		Conversion conversion = Conversion.from(from, to);
		Converter converter = converters.get(conversion);

		if (converter == null) {
			return String.format("No converter from %s to %s", from, to);
		}

		logger.info("Converting %s from %s to %s", new Object[] { value, from, to });
		return "" + converter.convert(value);
	}

	@GET
	@Path("/list")
	public String listAll(@QueryParam("from") String from) {
		String converterNames = converters.keySet().stream()
		.filter(conversion -> {
			if (from != null) {
				return conversion.from().equals(from);
			} else {
				return true;
			}
		})
				.map(Object::toString)
				.collect(Collectors.joining("\n"));
		
		return String.format("Got %s total converters.\n %s", converters.size(), converterNames );
	}

}