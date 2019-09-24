package no.ntnu.tdt4250.oving2.conversion.expression;

import java.util.regex.Pattern;

public class FieldExpressionConverter extends ExpressionBasedConverter {

	private static Pattern EXPRESSION_PATTERN = Pattern.compile("[a-zA-Z]+\\s*=.*[a-zA-Z].*");
	
	private String expression;
	
	public FieldExpressionConverter(String expression) {
		super();
		if (! EXPRESSION_PATTERN.matcher(expression).matches()) {
			throw new IllegalArgumentException("Invalid expression: " + expression);
		}
		this.expression = expression;
	}

	@Override
	protected String getExpression() {
		return expression;
	}
}
