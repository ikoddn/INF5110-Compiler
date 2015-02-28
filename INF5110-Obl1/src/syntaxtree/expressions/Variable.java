package syntaxtree.expressions;

import java.util.Arrays;
import java.util.List;

public class Variable extends Expression {

	private Expression expression;

	public Variable(String name) {
		this(name, null);
	}

	public Variable(String name, Expression expression) {
		super(name);

		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		StringBuilder sb = new StringBuilder();

		if (expression != null) {
			sb.append("( . ");
		}

		sb.append("(NAME ");
		sb.append(name);
		sb.append(")");

		if (expression != null) {
			sb.append(" (NAME ");
			sb.append(expression.getName());
			sb.append("))");
		}

		return Arrays.asList(sb.toString());
	}
}
