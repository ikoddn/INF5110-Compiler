package syntaxtree.expressions.operator;

import syntaxtree.expressions.Expression;
import syntaxtree.operators.LogicOperator;

public class LogicOperatorExpression extends BinaryOperatorExpression {

	public LogicOperatorExpression(Expression leftExpression,
			LogicOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}
}
