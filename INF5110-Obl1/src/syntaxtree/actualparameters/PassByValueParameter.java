package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.expressions.Expression;

public class PassByValueParameter extends ActualParameter {

	private Expression expression;

	public PassByValueParameter(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ACTUAL_PARAM").addInline(expression)
				.build();
	}
}
