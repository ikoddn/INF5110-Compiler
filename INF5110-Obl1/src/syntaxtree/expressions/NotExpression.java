package syntaxtree.expressions;

import java.util.List;

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
		return makeAstStringListWithInlineChild("NOT", expression);
	}
}
