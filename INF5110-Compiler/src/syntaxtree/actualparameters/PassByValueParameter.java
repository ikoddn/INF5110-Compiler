package syntaxtree.actualparameters;

import syntaxtree.expressions.Expression;
import bytecode.CodeProcedure;

public class PassByValueParameter extends ActualParameter {

	public PassByValueParameter(Expression expression) {
		super(false, expression);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		expression.generateCode(procedure);
	}
}
