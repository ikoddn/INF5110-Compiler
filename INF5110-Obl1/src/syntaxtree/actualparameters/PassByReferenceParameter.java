package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.expressions.Variable;

public class PassByReferenceParameter extends ActualParameter {

	private Variable variable;

	public PassByReferenceParameter(Variable variable) {
		this.variable = variable;
	}

	public Variable getVariable() {
		return variable;
	}

	@Override
	public List<String> makeAstStringList() {
		return makeAstStringListWithInlineChild("ACTUAL_PARAM ref", variable);
	}
}
