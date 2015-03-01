package syntaxtree.expressions.binaryoperators.arithmetic;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class Multiplication extends BinaryOperatorExpression {

	public Multiplication(Expression leftHandSide, Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("ARIT_OP *");
	}
}
