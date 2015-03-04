package syntaxtree.expressions;

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
	public List<String> makeAstStringList() {
		if (expression == null) {
			return name.makeAstStringList();
		}

		return makeAstStringListWithInlineChildren(" .", expression, name);
	}
}
