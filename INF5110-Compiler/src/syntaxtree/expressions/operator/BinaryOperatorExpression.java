package syntaxtree.expressions.operator;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.Operator;

public abstract class BinaryOperatorExpression<O extends Operator> extends
		Expression {

	protected Expression leftExpression;
	protected O operator;
	protected Expression rightExpression;

	protected BinaryOperatorExpression(Expression leftExpression, O operator,
			Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.operator = operator;
		this.rightExpression = rightExpression;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public O getOperator() {
		return operator;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder(operator.makeAstLabel()).addIndented(
				leftExpression, rightExpression).build();
	}
}
