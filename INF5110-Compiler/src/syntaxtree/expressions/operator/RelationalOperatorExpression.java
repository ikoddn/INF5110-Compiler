package syntaxtree.expressions.operator;

import syntaxtree.expressions.Expression;
import syntaxtree.operators.RelationalOperator;

public class RelationalOperatorExpression extends BinaryOperatorExpression {

	public RelationalOperatorExpression(Expression leftExpression,
			RelationalOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}
}
