package syntaxtree.expressions.binaryoperators.relational;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class GreaterThanOrEqualTo extends BinaryOperatorExpression {

	public GreaterThanOrEqualTo(Expression leftHandSide,
			Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("REL_OP >=");
	}
}
