package syntaxtree.expressions.binaryoperators.relational;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class GreaterThan extends BinaryOperatorExpression {

	public GreaterThan(Expression leftHandSide, Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("REL_OP >");
	}
}
