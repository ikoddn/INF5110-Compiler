package syntaxtree.expressions;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.Name;

public class Variable extends Expression {

	private Name name;
	private Expression expression;

	public Variable(Name name) {
		this(name, null);
	}

	public Variable(Name name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public Name getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstPrint() {
		if (expression == null) {
			return name.makeAstPrint();
		}

		List<String> result = new LinkedList<String>();

		result.add("( . ");
		addInline(result, expression);

		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		sb.append(name.makeAstString());
		sb.append(")");

		appendStringToLastElement(result, sb.toString());

		return result;
	}
}
