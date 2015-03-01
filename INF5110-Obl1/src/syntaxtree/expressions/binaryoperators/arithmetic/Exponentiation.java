package syntaxtree.expressions.binaryoperators.arithmetic;

import java.util.List;

import syntaxtree.expressions.Expression;
import syntaxtree.expressions.binaryoperators.BinaryOperatorExpression;

public class Exponentiation extends BinaryOperatorExpression {

	public Exponentiation(Expression base, Expression exponent) {
		super(base, exponent);
	}

	@Override
	public List<String> makeAstPrint() {
		return makeAstPrint("ARIT_OP #");
	}
}
