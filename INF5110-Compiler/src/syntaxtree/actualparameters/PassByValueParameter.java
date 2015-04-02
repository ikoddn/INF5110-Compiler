package syntaxtree.actualparameters;

import syntaxtree.expressions.Expression;

public class PassByValueParameter extends ActualParameter {

	public PassByValueParameter(Expression expression) {
		super(false, expression);
	}
}
