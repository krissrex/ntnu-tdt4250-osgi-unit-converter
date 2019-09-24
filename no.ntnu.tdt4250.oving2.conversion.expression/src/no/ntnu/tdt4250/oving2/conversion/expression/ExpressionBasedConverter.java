package no.ntnu.tdt4250.oving2.conversion.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.ntnu.tdt4250.oving2.conversion.Conversion;
import no.ntnu.tdt4250.oving2.conversion.Converter;

public abstract class ExpressionBasedConverter implements Converter {

	private static final Pattern WORD_REGEX = Pattern.compile("[a-zA-Z]+");
	private static final Logger logger = LoggerFactory.getLogger(ExpressionBasedConverter.class);
	
	/**
	 * @return the javascript expression, in a format {@code "to = from * 1 + 90"}. 
	 * 	The {@code from} variable in the expression can be used multiple places, if needed.
	 * 	Use the real unit names for {@code to} and {@code from}, eg {@code "F = C * 1.8 + 32"}.
	 */
	protected abstract String getExpression();
	
	@Override
	public Conversion conversion() {
		return fromExpression(getExpression());
	}

	@Override
	public double convert(double value) {
		return evaluateExpression(getExpression(), conversion(), value);
	}
	
	public static Conversion fromExpression(String expression) {
		String[] parts = expression.split("=");
		if (parts.length == 2) {
			String to = parts[0].strip();
			Matcher unitWordMatcher = WORD_REGEX.matcher(parts[1]);
			if (unitWordMatcher.find()) {
				String from = unitWordMatcher.group();				
				return Conversion.from(from, to);
			} else {
				throw new IllegalArgumentException("Failed to find 'from'-unit in \"" + expression + "\": " + parts[1]);
			}
		} else {
			throw new IllegalArgumentException("Invalid expression \"" + expression + "\"");
		}
	}

	public static double evaluateExpression(String expression, Conversion conversion, double value) {
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    Bindings bindings = engine.createBindings();
		bindings.put(conversion.from(), value);
	    engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
	    
	    try {
	    	String calculation = expression.split("=")[1];
	    	Object result = engine.eval(calculation);
	    	logger.info("Evaluated expression \"{}\" to: {}", expression, result);
	    	if (result instanceof Double) {
	    		return (Double) result;
	    	}
			throw new IllegalArgumentException("Expression did not evaluate to double: \"" + expression +"\" => " + result);
		} catch (ScriptException e) {
			throw new IllegalArgumentException("Script evaluation failed", e);
		}
	}
	
}
