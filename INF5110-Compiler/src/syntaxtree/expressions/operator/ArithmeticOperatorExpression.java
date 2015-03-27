package syntaxtree.expressions.operator;

import syntaxtree.expressions.Expression;
import syntaxtree.operators.ArithmeticOperator;

public class ArithmeticOperatorExpression extends BinaryOperatorExpression {

	public ArithmeticOperatorExpression(Expression leftExpression,
			ArithmeticOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}
}
