package syntaxtree.expressions.binaryoperators.relational;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class LessThanOrEqualTo extends BinaryOperatorExpression {

	public LessThanOrEqualTo(Expression leftHandSide, Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("REL_OP <=");
	}
}
