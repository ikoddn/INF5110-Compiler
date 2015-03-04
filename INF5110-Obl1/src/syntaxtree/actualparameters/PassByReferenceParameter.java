package syntaxtree.actualparameters;

import java.util.List;

import syntaxtree.AstStringListBuilder;
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
		return new AstStringListBuilder("ACTUAL_PARAM ref").addInline(variable)
				.build();
	}
}
