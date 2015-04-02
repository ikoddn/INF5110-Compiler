package syntaxtree.actualparameters;

import syntaxtree.expressions.Variable;

public class PassByReferenceParameter extends ActualParameter {

	public PassByReferenceParameter(Variable variable) {
		super(true, variable);
	}
}
