package syntaxtree.actualparameters;

import java.util.LinkedList;
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
	public List<String> makeAstPrint() {
		List<String> result = new LinkedList<String>();
		result.add("(ACTUAL_PARAM REF ");
		addInline(result, variable);
		appendStringToLastElement(result, ")");

		return result;
	}
}
