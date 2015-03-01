package syntaxtree.expressions.binaryoperators.relational;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class EqualTo extends BinaryOperatorExpression {

	public EqualTo(Expression leftHandSide, Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("REL_OP =");
	}
}
