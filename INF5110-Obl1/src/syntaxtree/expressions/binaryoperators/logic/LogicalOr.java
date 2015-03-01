package syntaxtree.expressions.binaryoperators.logic;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class LogicalOr extends BinaryOperatorExpression {

	public LogicalOr(Expression leftHandSide, Expression rightHandSide) {
		super(leftHandSide, rightHandSide);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("LOG_OP ||");
	}
}
