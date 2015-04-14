package syntaxtree.actualparameters;

import syntaxtree.expressions.Variable;
import bytecode.CodeProcedure;

public class PassByReferenceParameter extends ActualParameter {

	public PassByReferenceParameter(Variable variable) {
		super(true, variable);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		// Ref parameter not supported by bytecode
	}
}
