package syntaxtree.expressions;

import java.util.List;

import syntaxtree.AstStringListBuilder;

public class NotExpression extends Expression {

	private Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("NOT").addInline(expression).build();
	}
}
