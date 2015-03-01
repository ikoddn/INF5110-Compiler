package syntaxtree.expressions;

import java.util.LinkedList;
import java.util.List;

public class Variable extends Expression {

	private String name;
	private Expression expression;

	public Variable(String name) {
		this(name, null);
	}

	public Variable(String name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		StringBuilder sb = new StringBuilder();

		if (expression != null) {
			sb.append("( . ");
		}

		sb.append("(NAME ");
		sb.append(name);
		sb.append(")");

		if (expression == null) {
			sb.append(")");
			result.add(sb.toString());
		} else {
			sb.append(" ");
			result.add(sb.toString());

			addInline(result, expression.makeAstPrint());
			appendStringToLastElement(result, ")");
		}

		return result;
	}
}
