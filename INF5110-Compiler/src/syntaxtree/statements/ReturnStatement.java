package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.expressions.Expression;

public class ReturnStatement extends Statement {

	private Expression expression;

	public ReturnStatement() {
		this(null);
	}

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("RETURN_STMT").addInline(expression)
				.build();
	}
}
