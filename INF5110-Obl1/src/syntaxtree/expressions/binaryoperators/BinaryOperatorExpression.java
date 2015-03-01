package syntaxtree.expressions.binaryoperators;

import java.util.List;

import syntaxtree.expressions.Expression;

public abstract class BinaryOperatorExpression extends Expression {

	protected Expression leftHandSide;
	protected Expression rightHandSide;

	protected BinaryOperatorExpression(Expression leftHandSide,
			Expression rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Expression getLeftHandSide() {
		return leftHandSide;
	}

	public Expression getRightHandSide() {
		return rightHandSide;
	}

	protected List<String> makeAstPrint(String label) {
		return makeParentAstPrint(label, leftHandSide, rightHandSide);
	}
}
